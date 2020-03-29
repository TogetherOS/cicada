package top.crossoverjie.cicada.server.bootstrap;

/**
 * initialize something about db/redis/kafka etc.
 */
public abstract class InitializeHandle {

    /**
     *
     * @throws Exception
     */
    public abstract void handle() throws Exception;
}
