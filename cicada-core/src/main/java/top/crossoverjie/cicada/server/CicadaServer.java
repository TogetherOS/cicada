package top.crossoverjie.cicada.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import top.crossoverjie.cicada.server.config.AppConfig;
import top.crossoverjie.cicada.server.configuration.AbstractCicadaConfiguration;
import top.crossoverjie.cicada.server.configuration.ConfigurationHolder;
import top.crossoverjie.cicada.server.constant.CicadaConstant;
import top.crossoverjie.cicada.server.init.CicadaInitializer;
import top.crossoverjie.cicada.server.util.ClassScanner;
import top.crossoverjie.cicada.server.util.LoggerBuilder;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

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

    public static void start(Class<?> clazz,String path) throws Exception {

        long start = System.currentTimeMillis();


        //init application
        AppConfig.getInstance().setRootPackageName(clazz.getPackage().getName());
        AppConfig.getInstance().setRootPath(path);

        InputStream resourceAsStream = CicadaServer.class.getClassLoader().getResourceAsStream("application.properties");
        Properties prop = new Properties();
        prop.load(resourceAsStream);
        int port = Integer.parseInt(prop.get(CicadaConstant.CICADA_PORT).toString());

        AppConfig.getInstance().setPort(port);

        List<Class<?>> configuration = ClassScanner.getConfiguration(AppConfig.getInstance().getRootPackageName());
        for (Class<?> aClass : configuration) {
            AbstractCicadaConfiguration conf = (AbstractCicadaConfiguration) aClass.newInstance() ;
            InputStream stream = CicadaServer.class.getClassLoader().getResourceAsStream(conf.getPropertiesName());
            Properties properties = new Properties();
            properties.load(stream);
            conf.setProperties(properties) ;

            ConfigurationHolder.addConfiguration(aClass.getName(),conf) ;
        }

        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new CicadaInitializer());

            ChannelFuture future = bootstrap.bind(port).sync();
            if (future.isSuccess()) {
                long end = System.currentTimeMillis();
                LOGGER.info("Cicada started on port: {}.cost {}ms", port ,end-start);
            }
            Channel channel = future.channel();
            channel.closeFuture().sync();

        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }


    private void initConfig(){

    }
}
