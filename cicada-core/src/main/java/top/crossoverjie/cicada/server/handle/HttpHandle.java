package top.crossoverjie.cicada.server.handle;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import top.crossoverjie.cicada.server.action.WorkAction;
import top.crossoverjie.cicada.server.action.param.Param;
import top.crossoverjie.cicada.server.action.param.ParamMap;
import top.crossoverjie.cicada.server.action.res.WorkRes;
import top.crossoverjie.cicada.server.config.AppConfig;
import top.crossoverjie.cicada.server.enums.StatusEnum;
import top.crossoverjie.cicada.server.exception.CicadaException;
import top.crossoverjie.cicada.server.util.LoggerBuilder;
import top.crossoverjie.cicada.server.util.PathUtil;
import top.crossoverjie.cicada.server.util.Scanner;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/8/30 18:47
 * @since JDK 1.8
 */
public class HttpHandle extends ChannelInboundHandlerAdapter {

    private final static Logger LOGGER = LoggerBuilder.getLogger(HttpHandle.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof DefaultHttpRequest) {
            DefaultHttpRequest request = (DefaultHttpRequest) msg;

            String uri = request.uri();
            LOGGER.info("uri=[{}]", uri);
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(URLDecoder.decode(request.uri(), "utf-8"));

            // check Root Path
            AppConfig appConfig = checkRootPath(uri, queryStringDecoder);

            // check Action
            Class<?> actionClazz = checkAction(queryStringDecoder, appConfig);

            //build paramMap
            Param paramMap = buildParamMap(queryStringDecoder);




            // execute Method
            WorkAction action = (WorkAction) actionClazz.newInstance();
            WorkRes execute = action.execute(paramMap);

            // Response
            responseMsg(ctx, execute);

        }

    }

    /**
     * Response
     * @param ctx
     * @param execute
     */
    private void responseMsg(ChannelHandlerContext ctx, WorkRes execute) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(JSON.toJSONString(execute), CharsetUtil.UTF_8));
        buildHeader(response);
        ctx.writeAndFlush(response);
    }

    /**
     * build paramMap
     * @param queryStringDecoder
     * @return
     */
    private Param buildParamMap(QueryStringDecoder queryStringDecoder) {
        Map<String, List<String>> parameters = queryStringDecoder.parameters();
        Param paramMap = new ParamMap() ;
        for (Map.Entry<String, List<String>> stringListEntry : parameters.entrySet()) {
            String key = stringListEntry.getKey();
            List<String> value = stringListEntry.getValue();
            paramMap.put(key,value.get(0)) ;
        }
        return paramMap;
    }

    /**
     * check Action
     * @param queryStringDecoder
     * @param appConfig
     * @return
     * @throws Exception
     */
    private Class<?> checkAction(QueryStringDecoder queryStringDecoder, AppConfig appConfig) throws Exception {
        String actionPath = PathUtil.getActionPath(queryStringDecoder.path());
        Map<String, Class<?>> cicadaAction = Scanner.getCicadaAction(appConfig.getRootPackageName());
        Class<?> actionClazz = cicadaAction.get(actionPath);
        if (actionClazz == null){
            throw new CicadaException(StatusEnum.REQUEST_ERROR,actionPath + " Not Fount") ;
        }
        return actionClazz;
    }

    /**
     * check Root Path
     * @param uri
     * @param queryStringDecoder
     * @return
     */
    private AppConfig checkRootPath(String uri, QueryStringDecoder queryStringDecoder) {
        AppConfig appConfig = AppConfig.getInstance();
        if (!PathUtil.getRootPath(queryStringDecoder.path()).equals(appConfig.getRootPath())){
            throw new CicadaException(StatusEnum.REQUEST_ERROR,uri) ;
        }
        return appConfig;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        WorkRes workRes = new WorkRes() ;
        workRes.setCode(String.valueOf(HttpResponseStatus.NOT_FOUND.code()));
        workRes.setMessage(cause.getMessage());

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND, Unpooled.copiedBuffer(JSON.toJSONString(workRes), CharsetUtil.UTF_8)) ;
        buildHeader(response);
        ctx.writeAndFlush(response);
    }

    /**
     * build Header
     * @param response
     */
    private void buildHeader(DefaultFullHttpResponse response) {
        HttpHeaders headers = response.headers();
        headers.setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        headers.set(HttpHeaderNames.CONTENT_TYPE, "application/json");
    }
}
