package top.crossoverjie.cicada.server.enums;

import java.util.ArrayList;
import java.util.List;

public enum StatusEnum {

    /** success */
    SUCCESS("9000", "success"),


    /** request Error */
    REQUEST_ERROR("7000", "Request Error"),

    /** duplicate ioc impl*/
    DUPLICATE_IOC("8000", "Duplicate ioc impl error"),

    /** empty of package */
    NULL_PACKAGE("8000", "Your main class is empty of package"),

    REPEAT_ROUTE("8000","Request correspond multiple method"),

    /** 404 */
    NOT_FOUND("404", "Need to declare a method by using @CicadaRoute!"),

    /** IllegalArgumentException */
    ILLEGAL_PARAMETER("404", "IllegalArgumentException: You can only have two parameters at most by using @CicadaRoute!"),
    ;


    /** 枚举值码 */
    private final String code;

    /** 枚举描述 */
    private final String message;

    /**
     * 构建一个 StatusEnum 。
     * @param code 枚举值码。
     * @param message 枚举描述。
     */
    private StatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 得到枚举值码。
     * @return 枚举值码。
     */
    public String getCode() {
        return code;
    }

    /**
     * 得到枚举描述。
     * @return 枚举描述。
     */
    public String getMessage() {
        return message;
    }

    /**
     * 得到枚举值码。
     * @return 枚举值码。
     */
    public String code() {
        return code;
    }

    /**
     * 得到枚举描述。
     * @return 枚举描述。
     */
    public String message() {
        return message;
    }

    /**
     * 通过枚举值码查找枚举值。
     * @param code 查找枚举值的枚举值码。
     * @return 枚举值码对应的枚举值。
     * @throws IllegalArgumentException 如果 code 没有对应的 StatusEnum 。
     */
    public static StatusEnum findStatus(String code) {
        for (StatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("ResultInfo StatusEnum not legal:" + code);
    }

    /**
     * 获取全部枚举值。
     *
     * @return 全部枚举值。
     */
    public static List<StatusEnum> getAllStatus() {
        List<StatusEnum> list = new ArrayList<StatusEnum>();
        for (StatusEnum status : values()) {
            list.add(status);
        }
        return list;
    }

    /**
     * 获取全部枚举值码。
     *
     * @return 全部枚举值码。
     */
    public static List<String> getAllStatusCode() {
        List<String> list = new ArrayList<String>();
        for (StatusEnum status : values()) {
            list.add(status.code());
        }
        return list;
    }
}
