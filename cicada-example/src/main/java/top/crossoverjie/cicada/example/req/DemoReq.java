package top.crossoverjie.cicada.example.req;

import top.crossoverjie.cicada.server.action.req.WorkReq;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/8/31 18:59
 * @since JDK 1.8
 */
public class DemoReq extends WorkReq {

    private Integer id ;
    private String name ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DemoReq{" +
                "id=" + id +
                ", name='" + name + '\'' +
                "} " + super.toString();
    }
}
