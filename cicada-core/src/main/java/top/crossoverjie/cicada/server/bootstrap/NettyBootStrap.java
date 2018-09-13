package top.crossoverjie.cicada.server.bootstrap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import top.crossoverjie.cicada.server.config.AppConfig;
import top.crossoverjie.cicada.server.configuration.ApplicationConfiguration;
import top.crossoverjie.cicada.server.constant.CicadaConstant;
import top.crossoverjie.cicada.server.init.CicadaInitializer;
import top.crossoverjie.cicada.server.thread.ThreadLocalHolder;
import top.crossoverjie.cicada.server.util.LoggerBuilder;

import static top.crossoverjie.cicada.server.configuration.ConfigurationHolder.getConfiguration;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/9/10 21:56
 * @since JDK 1.8
 */
public class NettyBootStrap {

    private final static Logger LOGGER = LoggerBuilder.getLogger(NettyBootStrap.class);

    private static final int BOSS_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    private static EventLoopGroup boss = new NioEventLoopGroup(BOSS_SIZE);
    private static EventLoopGroup work = new NioEventLoopGroup();

    /**
     * Start netty Server
     *
     * @throws Exception
     */
    public static void startServer() throws Exception {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new CicadaInitializer());

            ChannelFuture future = bootstrap.bind(AppConfig.getInstance().getPort()).sync();
            if (future.isSuccess()) {
                appLog();
            }
            Channel channel = future.channel();
            channel.closeFuture().sync();

        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    private static void appLog() {
        Long start = ThreadLocalHolder.getLocalTime();
        ApplicationConfiguration applicationConfiguration = (ApplicationConfiguration) getConfiguration(ApplicationConfiguration.class);
        long end = System.currentTimeMillis();
        LOGGER.info("Cicada started on port: {}.cost {}ms", applicationConfiguration.get(CicadaConstant.CICADA_PORT), end - start);
    }
}
