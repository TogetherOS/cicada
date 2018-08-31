package top.crossoverjie.cicada.server.model;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/8/31 14:48
 * @since JDK 1.8
 */
public class Msg {

    private long requestId ;

    private String msg ;

    public Msg(long requestId, String msg) {
        this.requestId = requestId;
        this.msg = msg;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
