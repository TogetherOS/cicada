package top.crossoverjie.cicada.server.action.req;

import io.netty.handler.codec.http.DefaultHttpRequest;

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

    private CicadaHttpRequest(){}

    public static CicadaHttpRequest init(DefaultHttpRequest httpRequest){
        CicadaHttpRequest request = new CicadaHttpRequest() ;
        request.method = httpRequest.method().name();
        request.url = httpRequest.uri();

        return request ;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public String getUrl() {
        return this.url;
    }
}
