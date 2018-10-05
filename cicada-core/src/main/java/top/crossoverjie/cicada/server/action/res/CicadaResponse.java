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

    /**
     * get all customer headers
     * @return
     */
    Map<String, String> getHeaders();


    /**
     * set content type
     * @param contentType
     */
    void setContentType(String contentType);

    /**
     * get content type
     * @return
     */
    String getContentType();

    /**
     * set http body
     * @param content
     */
    void setHttpContent(String content);

    /**
     * get http body
     * @return
     */
    String getHttpContent();

}
