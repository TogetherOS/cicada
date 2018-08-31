package top.crossoverjie.cicada.server.http;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.crossoverjie.cicada.server.action.WorkAction;
import top.crossoverjie.cicada.server.action.req.WorkReq;
import top.crossoverjie.cicada.server.action.res.WorkRes;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/8/30 18:47
 * @since JDK 1.8
 */
public class HttpHandle extends ChannelInboundHandlerAdapter {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpHandle.class);

    private final StringBuilder sb = new StringBuilder() ;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof DefaultHttpRequest){
            DefaultHttpRequest request = (DefaultHttpRequest) msg;

            String uri = request.uri();
            LOGGER.info("uri=[{}]",uri) ;

            Class<?> reqClazz = Class.forName("top.crossoverjie.cicada.example.req.DemoReq");
            WorkReq workReq = (WorkReq) reqClazz.newInstance();



            Class<?> workActionClazz = Class.forName("top.crossoverjie.cicada.example.action.DemoAction");
            WorkAction action = (WorkAction) workActionClazz.newInstance();
            WorkRes execute = action.execute(workReq) ;

            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(JSON.toJSONString(execute), CharsetUtil.UTF_8));
            HttpHeaders headers = response.headers();
            headers.setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            headers.set(HttpHeaderNames.CONTENT_TYPE,"application/json");
            ctx.writeAndFlush(response) ;

        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
