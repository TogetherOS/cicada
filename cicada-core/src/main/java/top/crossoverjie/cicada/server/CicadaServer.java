package top.crossoverjie.cicada.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import top.crossoverjie.cicada.server.config.AppConfig;
import top.crossoverjie.cicada.server.init.CicadaInitializer;
import top.crossoverjie.cicada.server.init.CicadaConfigInitializer;
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

    private static final int BOSS_SIZE = Runtime.getRuntime().availableProcessors() * 2;

    private static EventLoopGroup boss = new NioEventLoopGroup(BOSS_SIZE);
    private static EventLoopGroup work = new NioEventLoopGroup();

    public static void start(Class<?> clazz, String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();

        try {
            // 配置文件初始化
            CicadaConfigInitializer.init(clazz, args);
            int port = AppConfig.getInstance().getPort();
            String path = AppConfig.getInstance().getRootPath();
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new CicadaInitializer());

            ChannelFuture future = bootstrap.bind(port).sync();
            if (future.isSuccess()) {
                long end = System.currentTimeMillis();
                LOGGER.info("Cicada started on port: {}. pathName: {}. cost {}ms", port ,path, end-start);
            }
            Channel channel = future.channel();
            channel.closeFuture().sync();

        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}
