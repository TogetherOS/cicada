package top.crossoverjie.cicada.server.action.req;

/**
 * @author crossoverJie
 */
public class WorkReq {

    private Integer timeStamp;

    public Integer getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Integer timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "WorkReq{" +
                "timeStamp=" + timeStamp +
                '}';
    }
}
