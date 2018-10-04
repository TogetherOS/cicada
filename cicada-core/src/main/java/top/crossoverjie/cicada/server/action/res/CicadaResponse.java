package top.crossoverjie.cicada.server.action.res;

import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/10/5 00:53
 * @since JDK 1.8
 */
public interface CicadaResponse {

    Map<String, String> getHeaders();


    void setContentType(String contentType);

    String getContentType();

    void setHttpContent(String content);

    String getHttpContent();

}
