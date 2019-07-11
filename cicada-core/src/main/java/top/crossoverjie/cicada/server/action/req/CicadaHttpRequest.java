package top.crossoverjie.cicada.server.action.req;

import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import top.crossoverjie.cicada.server.constant.CicadaConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/10/5 00:42
 * @since JDK 1.8
 */
public class CicadaHttpRequest implements CicadaRequest {

    private String method ;

    private String url ;

    private String clientAddress ;

    private Map<String,Cookie> cookie = new HashMap<>(8) ;
    private Map<String,String> headers = new HashMap<>(8) ;

    private CicadaHttpRequest(){}

    public static CicadaHttpRequest init(DefaultHttpRequest httpRequest){
        CicadaHttpRequest request = new CicadaHttpRequest() ;
        request.method = httpRequest.method().name();
        request.url = httpRequest.uri();

        //build headers
        buildHeaders(httpRequest, request);

        //initBean cookies
        initCookies(request);

        return request ;
    }

    /**
     * build headers
     * @param httpRequest io.netty.httprequest
     * @param request cicada request
     */
    private static void buildHeaders(DefaultHttpRequest httpRequest, CicadaHttpRequest request) {
        for (Map.Entry<String, String> entry : httpRequest.headers().entries()) {
            request.headers.put(entry.getKey(),entry.getValue());
        }
    }

    /**
     * initBean cookies
     * @param request request
     */
    private static void initCookies(CicadaHttpRequest request) {
        for (Map.Entry<String, String> entry : request.headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!key.equals(CicadaConstant.ContentType.COOKIE)){
                continue;
            }

            for (io.netty.handler.codec.http.cookie.Cookie cookie : ServerCookieDecoder.LAX.decode(value)) {
                Cookie cicadaCookie = new Cookie() ;
                cicadaCookie.setName(cookie.name());
                cicadaCookie.setValue(cookie.value());
                cicadaCookie.setDomain(cookie.domain());
                cicadaCookie.setMaxAge(cookie.maxAge());
                cicadaCookie.setPath(cookie.path()) ;
                request.cookie.put(cicadaCookie.getName(),cicadaCookie) ;
            }
        }
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public Cookie getCookie(String key) {
        return cookie.get(key) ;
    }
}
