package top.crossoverjie.cicada.server.action.res;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/8/31 16:03
 * @since JDK 1.8
 */
public class WorkRes<T> {
    private String code;

    private String message;

    private T dataBody ;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getDataBody() {
        return dataBody;
    }

    public void setDataBody(T dataBody) {
        this.dataBody = dataBody;
    }
}
