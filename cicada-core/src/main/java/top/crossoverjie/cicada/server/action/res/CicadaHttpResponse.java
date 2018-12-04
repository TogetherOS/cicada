package top.crossoverjie.cicada.server.action.res;

import io.netty.handler.codec.http.cookie.DefaultCookie;
import top.crossoverjie.cicada.server.action.req.Cookie;
import top.crossoverjie.cicada.server.constant.CicadaConstant;
import top.crossoverjie.cicada.server.exception.CicadaException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/10/5 01:13
 * @since JDK 1.8
 */
public class CicadaHttpResponse implements CicadaResponse {

    private Map<String, String> headers = new HashMap<>(8);

    private String contentType;

    private String httpContent;

    private List<io.netty.handler.codec.http.cookie.Cookie> cookies = new ArrayList<>(6);

    private CicadaHttpResponse() {
    }

    public static CicadaHttpResponse init() {
        CicadaHttpResponse response = new CicadaHttpResponse();
        response.contentType = CicadaConstant.ContentType.TEXT;
        return response;
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public void setHttpContent(String content) {
        httpContent = content;
    }

    @Override
    public String getHttpContent() {
        return this.httpContent == null ? "" : this.httpContent;
    }

    public void setHeaders(String key, String value) {
        this.headers.put(key, value);
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }


    @Override
    public void setCookie(Cookie cicadaCookie) {
        if (null == cicadaCookie){
            throw new CicadaException("cookie is null!") ;
        }

        if (null == cicadaCookie.getName()){
            throw new CicadaException("cookie.getName() is null!") ;
        }
        if (null == cicadaCookie.getValue()){
            throw new CicadaException("cookie.getValue() is null!") ;
        }

        DefaultCookie cookie = new DefaultCookie(cicadaCookie.getName(), cicadaCookie.getValue());

        cookie.setPath("/");
        cookie.setMaxAge(cicadaCookie.getMaxAge());
        cookies.add(cookie) ;
    }

    @Override
    public List<io.netty.handler.codec.http.cookie.Cookie> cookies() {
        return cookies;
    }


}
