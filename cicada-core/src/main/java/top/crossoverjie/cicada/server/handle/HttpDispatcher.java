package top.crossoverjie.cicada.server.handle;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.Cookie;
import org.slf4j.Logger;
import top.crossoverjie.cicada.base.log.LoggerBuilder;
import top.crossoverjie.cicada.server.action.param.Param;
import top.crossoverjie.cicada.server.action.param.ParamMap;
import top.crossoverjie.cicada.server.action.req.CicadaHttpRequest;
import top.crossoverjie.cicada.server.action.req.CicadaRequest;
import top.crossoverjie.cicada.server.action.res.CicadaHttpResponse;
import top.crossoverjie.cicada.server.action.res.CicadaResponse;
import top.crossoverjie.cicada.server.bean.CicadaBeanManager;
import top.crossoverjie.cicada.server.config.AppConfig;
import top.crossoverjie.cicada.server.constant.CicadaConstant;
import top.crossoverjie.cicada.server.context.CicadaContext;
import top.crossoverjie.cicada.server.exception.CicadaException;
import top.crossoverjie.cicada.server.exception.GlobalHandelException;
import top.crossoverjie.cicada.server.intercept.InterceptProcess;
import top.crossoverjie.cicada.server.route.RouteProcess;
import top.crossoverjie.cicada.server.route.RouterScanner;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/8/30 18:47
 * @since JDK 1.8
 */
@ChannelHandler.Sharable
public final class HttpDispatcher extends SimpleChannelInboundHandler<DefaultHttpRequest> {

    private static final Logger LOGGER = LoggerBuilder.getLogger(HttpDispatcher.class);

    private final AppConfig appConfig = AppConfig.getInstance();
    private final InterceptProcess interceptProcess = InterceptProcess.getInstance();
    private final RouterScanner routerScanner = RouterScanner.getInstance();
    private final RouteProcess routeProcess = RouteProcess.getInstance() ;
    private final CicadaBeanManager cicadaBeanManager = CicadaBeanManager.getInstance() ;
    private final GlobalHandelException exceptionHandle = cicadaBeanManager.exceptionHandle() ;
    private Exception exception ;

    @Override
    public void channelRead0(ChannelHandlerContext ctx, DefaultHttpRequest httpRequest) {

        CicadaRequest cicadaRequest = CicadaHttpRequest.init(httpRequest);
        CicadaResponse cicadaResponse = CicadaHttpResponse.init();

        // set current thread request and response
        CicadaContext.setContext(new CicadaContext(cicadaRequest, cicadaResponse));

        try {
            // request uri
            String uri = cicadaRequest.getUrl();
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(URLDecoder.decode(httpRequest.uri(), "utf-8"));

            // check Root Path
            appConfig.checkRootPath(uri, queryStringDecoder);

            // route Action
            //Class<?> actionClazz = routeAction(queryStringDecoder, appConfig);

            //build paramMap
            Param paramMap = buildParamMap(queryStringDecoder);

            //load interceptors
            interceptProcess.loadInterceptors();

            //interceptor before
            boolean access = interceptProcess.processBefore(paramMap);
            if (!access) {
                return;
            }

            // execute Method
            Method method = routerScanner.routeMethod(cicadaRequest.getMethod(),queryStringDecoder);
            routeProcess.invoke(method,queryStringDecoder) ;


            //WorkAction action = (WorkAction) actionClazz.newInstance();
            //action.execute(CicadaContext.getContext(), paramMap);


            // interceptor after
            interceptProcess.processAfter(paramMap);

        } catch (Exception e) {
            exceptionCaught(ctx, e);
        } finally {
            // Response
            responseContent(ctx);

            // remove cicada thread context
            CicadaContext.removeContext();
        }


    }


    /**
     * Response
     *
     * @param ctx
     */
    private void responseContent(ChannelHandlerContext ctx) {
        CicadaResponse cicadaResponse = CicadaContext.getResponse();
        String context = cicadaResponse.getHttpContent() ;

        ByteBuf buf = Unpooled.wrappedBuffer(context.getBytes(StandardCharsets.UTF_8));
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
        buildHeader(response);
        ctx.writeAndFlush(response);
    }

    /**
     * build paramMap
     *
     * @param queryStringDecoder
     * @return
     */
    private Param buildParamMap(QueryStringDecoder queryStringDecoder) {
        Map<String, List<String>> parameters = queryStringDecoder.parameters();
        Param paramMap = new ParamMap();
        for (Map.Entry<String, List<String>> stringListEntry : parameters.entrySet()) {
            String key = stringListEntry.getKey();
            List<String> value = stringListEntry.getValue();
            paramMap.put(key, value.get(0));
        }
        return paramMap;
    }




    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        exception = (Exception) cause;
        if (CicadaException.isResetByPeer(cause.getMessage())){
            return;
        }

        if (exceptionHandle != null){
            exceptionHandle.resolveException(CicadaContext.getContext(),exception);
        }
    }

    /**
     * build Header
     *
     * @param response
     */
    private void buildHeader(DefaultFullHttpResponse response) {
        CicadaResponse cicadaResponse = CicadaContext.getResponse();

        HttpHeaders headers = response.headers();
        headers.setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        headers.set(HttpHeaderNames.CONTENT_TYPE, cicadaResponse.getContentType());

        List<Cookie> cookies = cicadaResponse.cookies();
        for (Cookie cookie : cookies) {
            headers.add(CicadaConstant.ContentType.SET_COOKIE, io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode(cookie));
        }

    }
}
