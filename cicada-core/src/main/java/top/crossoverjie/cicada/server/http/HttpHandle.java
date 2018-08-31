package top.crossoverjie.cicada.server.http;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.crossoverjie.cicada.server.model.Msg;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/8/30 18:47
 * @since JDK 1.8
 */
public class HttpHandle extends ChannelInboundHandlerAdapter {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpHandle.class);


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof DefaultHttpRequest){
            DefaultHttpRequest request = (DefaultHttpRequest) msg;
            LOGGER.info("request={}" ,request.toString());

            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(JSON.toJSONString(new Msg(1234L,"hello")), CharsetUtil.UTF_8));
            response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"application/json");
            ctx.writeAndFlush(response) ;

        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
