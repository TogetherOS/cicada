package top.crossoverjie.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.crossoverjie.server.init.CicadaInitializer;

import java.net.InetSocketAddress;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/8/30 12:48
 * @since JDK 1.8
 */
public class CicadaServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(CicadaServer.class);

    private static EventLoopGroup boss = new NioEventLoopGroup();
    private static EventLoopGroup work = new NioEventLoopGroup();

    public static void main(String[] args) throws InterruptedException {

        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(boss, work)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(7317))
                .childHandler(new CicadaInitializer());

        ChannelFuture future = bootstrap.bind().sync();
        if (future.isSuccess()) {
            LOGGER.info("启动 Netty 成功");
        }
    }
}
