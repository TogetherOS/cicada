package top.crossoverjie.cicada.server.route.register;

import com.google.common.base.Objects;

import java.lang.reflect.Method;

/**
 * @author TANG
 */
public class RouterMethodInfo {

    private String path;

    private Method method;

    public RouterMethodInfo(String path, Method method) {
        this.path = path;
        this.method = method;
    }

    public RouterMethodInfo() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouterMethodInfo that = (RouterMethodInfo) o;
        return Objects.equal(path, that.path) &&
                Objects.equal(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(path, method);
    }
}
