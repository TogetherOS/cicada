package top.crossoverjie.cicada.server.route.register;

import com.google.common.base.Objects;
import top.crossoverjie.cicada.server.enums.HttpMethod;

public class RequestRouteInfo {

    private HttpMethod httpMethods;

    private String path;

    public RequestRouteInfo(String path, HttpMethod httpMethods) {
        this.httpMethods = httpMethods;
        this.path = path;
    }

    public RequestRouteInfo(){}

    public HttpMethod getHttpMethods() {
        return httpMethods;
    }

    public void setHttpMethods(HttpMethod httpMethods) {
        this.httpMethods = httpMethods;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestRouteInfo that = (RequestRouteInfo) o;
        return httpMethods == that.httpMethods &&
                Objects.equal(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(httpMethods, path);
    }
}
