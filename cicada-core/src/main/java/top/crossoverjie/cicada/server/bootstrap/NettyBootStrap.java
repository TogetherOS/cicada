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
import top.crossoverjie.cicada.server.context.CicadaContext;
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

    private static EventLoopGroup boss = new NioEventLoopGroup(1);
    private static EventLoopGroup work = new NioEventLoopGroup();
    private static Channel channel ;

    /**
     * Start netty Server
     *
     * @throws Exception
     */
    public static void startCicada() throws Exception {
        // start
        startServer();

        // register shutdown hook
        shutDownServer();

        // synchronized channel
        joinServer();
    }

    /**
     * start netty server
     * @throws InterruptedException
     */
    private static void startServer() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(boss, work)
                .channel(NioServerSocketChannel.class)
                .childHandler(new CicadaInitializer());

        ChannelFuture future = bootstrap.bind(AppConfig.getInstance().getPort()).sync();
        if (future.isSuccess()) {
            appLog();
        }
        channel = future.channel();
    }

    private static void joinServer() throws Exception {
        channel.closeFuture().sync();
    }

    private static void appLog() {
        Long start = ThreadLocalHolder.getLocalTime();
        ApplicationConfiguration applicationConfiguration = (ApplicationConfiguration) getConfiguration(ApplicationConfiguration.class);
        long end = System.currentTimeMillis();
        LOGGER.info("Cicada started on port: {}.cost {}ms", applicationConfiguration.get(CicadaConstant.CICADA_PORT), end - start);
    }

    /**
     * shutdown server
     */
    private static void shutDownServer() {
        ShutDownThread shutDownThread = new ShutDownThread();
        shutDownThread.setName("cicada-shutdown");
        Runtime.getRuntime().addShutdownHook(shutDownThread);
    }

    private static class ShutDownThread extends Thread {
        @Override
        public void run() {
            LOGGER.info("Cicada server stop...");
            CicadaContext.removeContext();

            boss.shutdownGracefully();
            work.shutdownGracefully();

            LOGGER.info("Cicada server has been successfully stopped.");
        }

    }
}
