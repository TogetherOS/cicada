package top.crossoverjie.cicada.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import top.crossoverjie.cicada.server.init.CicadaInitializer;
import top.crossoverjie.cicada.server.util.LoggerBuilder;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/8/30 12:48
 * @since JDK 1.8
 */
public class CicadaServer {

    private final static Logger LOGGER = LoggerBuilder.getLogger(CicadaServer.class) ;

    private static EventLoopGroup boss = new NioEventLoopGroup();
    private static EventLoopGroup work = new NioEventLoopGroup();

    public static void start(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();

        int port = 7317;
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new CicadaInitializer());

            ChannelFuture future = bootstrap.bind(port).sync();
            if (future.isSuccess()) {
                long end = System.currentTimeMillis();
                LOGGER.info("start server success,port=[{}] for [{}]ms", port ,end-start);
            }
            Channel channel = future.channel();
            channel.closeFuture().sync();

        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}
