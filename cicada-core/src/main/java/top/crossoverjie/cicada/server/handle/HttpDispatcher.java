package top.crossoverjie.cicada.server.handle;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import top.crossoverjie.cicada.server.action.param.Param;
import top.crossoverjie.cicada.server.action.param.ParamMap;
import top.crossoverjie.cicada.server.action.req.CicadaHttpRequest;
import top.crossoverjie.cicada.server.action.req.CicadaRequest;
import top.crossoverjie.cicada.server.action.res.CicadaHttpResponse;
import top.crossoverjie.cicada.server.action.res.CicadaResponse;
import top.crossoverjie.cicada.server.action.res.WorkRes;
import top.crossoverjie.cicada.server.config.AppConfig;
import top.crossoverjie.cicada.server.context.CicadaContext;
import top.crossoverjie.cicada.server.enums.StatusEnum;
import top.crossoverjie.cicada.server.exception.CicadaException;
import top.crossoverjie.cicada.server.intercept.InterceptProcess;
import top.crossoverjie.cicada.server.route.RouteProcess;
import top.crossoverjie.cicada.server.route.RouterScanner;
import top.crossoverjie.cicada.server.util.ClassScanner;
import top.crossoverjie.cicada.server.util.PathUtil;

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
public class HttpDispatcher extends SimpleChannelInboundHandler<DefaultHttpRequest> {

    private final InterceptProcess interceptProcess = InterceptProcess.getInstance();
    private final RouterScanner routerScanner = RouterScanner.getInstance();
    private final RouteProcess routeProcess = RouteProcess.getInstance() ;

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
            AppConfig appConfig = checkRootPath(uri, queryStringDecoder);

            // route Action
            //Class<?> actionClazz = routeAction(queryStringDecoder, appConfig);

            //build paramMap
            Param paramMap = buildParamMap(queryStringDecoder);

            //load interceptors
            interceptProcess.loadInterceptors(appConfig);

            //interceptor before
            boolean access = interceptProcess.processBefore(paramMap);
            if (!access) {
                return;
            }

            // execute Method
            Method method = routerScanner.routeMethod(queryStringDecoder, appConfig.getRootPackageName());
            routeProcess.invoke(method,queryStringDecoder) ;
            //需要从 IOC 容器中获取
            //method.invoke(method.getDeclaringClass().newInstance());

            //WorkAction action = (WorkAction) actionClazz.newInstance();
            //action.execute(CicadaContext.getContext(), paramMap);

            // interceptor after
            interceptProcess.processAfter(paramMap);

        } catch (Exception e) {
            exceptionCaught(ctx, e);
        } finally {
            // Response
            responseContent(ctx, CicadaContext.getResponse().getHttpContent());

            // remove cicada thread context
            CicadaContext.removeContext();
        }


    }


    /**
     * Response
     *
     * @param ctx
     */
    private void responseContent(ChannelHandlerContext ctx, String context) {

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

    /**
     * route Action
     *
     * @param queryStringDecoder
     * @param appConfig
     * @return
     * @throws Exception
     */
    private Class<?> routeAction(QueryStringDecoder queryStringDecoder, AppConfig appConfig) throws Exception {
        String actionPath = PathUtil.getActionPath(queryStringDecoder.path());
        Map<String, Class<?>> cicadaAction = ClassScanner.getCicadaAction(appConfig.getRootPackageName());

        if (cicadaAction == null) {
            throw new CicadaException("Must be configured WorkAction Object");
        }

        Class<?> actionClazz = cicadaAction.get(actionPath);
        if (actionClazz == null) {
            throw new CicadaException(StatusEnum.REQUEST_ERROR, actionPath + " Not Fount");
        }
        return actionClazz;
    }

    /**
     * check Root Path
     *
     * @param uri
     * @param queryStringDecoder
     * @return
     */
    private AppConfig checkRootPath(String uri, QueryStringDecoder queryStringDecoder) {
        AppConfig appConfig = AppConfig.getInstance();
        if (!PathUtil.getRootPath(queryStringDecoder.path()).equals(appConfig.getRootPath())) {
            throw new CicadaException(StatusEnum.REQUEST_ERROR, uri);
        }
        return appConfig;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        if (CicadaException.isResetByPeer(cause.getMessage())){
            return;
        }

        WorkRes workRes = new WorkRes();
        workRes.setCode(String.valueOf(HttpResponseStatus.NOT_FOUND.code()));
        workRes.setMessage(cause.getMessage());

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND, Unpooled.copiedBuffer(JSON.toJSONString(workRes), CharsetUtil.UTF_8));
        buildHeader(response);
        ctx.writeAndFlush(response);
    }

    /**
     * build Header
     *
     * @param response
     */
    private void buildHeader(DefaultFullHttpResponse response) {
        HttpHeaders headers = response.headers();
        headers.setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        headers.set(HttpHeaderNames.CONTENT_TYPE, CicadaContext.getResponse().getContentType());
    }
}
