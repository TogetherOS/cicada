package top.crossoverjie.cicada.server.action.req;

/**
 * Function: cookie
 *
 * @author crossoverJie
 *         Date: 2018/12/4 18:56
 * @since JDK 1.8
 */
public class Cookie {

    private String name ;
    private String value ;
    private String path ;
    private String domain ;
    private long maxAge ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(long maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public String toString() {
        return "Cookie{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", path='" + path + '\'' +
                ", domain='" + domain + '\'' +
                ", maxAge=" + maxAge +
                '}';
    }
}
