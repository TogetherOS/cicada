package top.crossoverjie.cicada.db.core.handle;

import lombok.extern.slf4j.Slf4j;
import top.crossoverjie.cicada.db.listener.DataChangeListener;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-12-04 00:00
 * @since JDK 1.8
 */
@Slf4j
public class HandleProxy<T> {

    private Class<T> clazz;

    public HandleProxy(Class<T> clazz) {
        this.clazz = clazz;
    }

    private DataChangeListener listener ;

    public T getInstance(DataChangeListener listener) {
        this.listener = listener ;
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] {clazz}, new ProxyInvocation(DBHandleImpl.class));
    }
    public T getInstance() {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] {clazz}, new ProxyInvocation(DBHandleImpl.class));
    }


    private class ProxyInvocation implements InvocationHandler {

        private Object target ;

        public ProxyInvocation(Class clazz){
            try {
                this.target = clazz.newInstance() ;
            } catch (Exception e) {
                log.error("exception={}",e);
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object invoke = method.invoke(target, args);
            if (null != listener){
                listener.listener(args[0]);
            }
            return invoke ;
        }
    }
}
