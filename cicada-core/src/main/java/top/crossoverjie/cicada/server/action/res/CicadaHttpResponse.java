package top.crossoverjie.cicada.server.action.res;

import top.crossoverjie.cicada.server.constant.CicadaConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/10/5 01:13
 * @since JDK 1.8
 */
public class CicadaHttpResponse implements CicadaResponse {

    private Map<String,String> headers = new HashMap<>(8) ;

    private String contentType ;

    private String httpContent ;

    private CicadaHttpResponse(){}

    public static CicadaHttpResponse init(){
        CicadaHttpResponse response = new CicadaHttpResponse() ;
        response.contentType = CicadaConstant.ContentType.TEXT;
        return response ;
    }

    @Override
    public void setContentType(String contentType){
        this.contentType = contentType ;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public void setHttpContent(String content) {
        httpContent = content ;
    }

    @Override
    public String getHttpContent() {
        return this.httpContent;
    }

    public void setHeaders(String key, String value){
        this.headers.put(key, value);
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

}
