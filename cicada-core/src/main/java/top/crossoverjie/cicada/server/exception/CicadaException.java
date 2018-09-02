package top.crossoverjie.cicada.server.exception;


import top.crossoverjie.cicada.server.enums.StatusEnum;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/8/25 15:26
 * @since JDK 1.8
 */
public class CicadaException extends GenericException {


    public CicadaException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public CicadaException(Exception e, String errorCode, String errorMessage) {
        super(e, errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public CicadaException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public CicadaException(StatusEnum statusEnum) {
        super(statusEnum.getMessage());
        this.errorMessage = statusEnum.message();
        this.errorCode = statusEnum.getCode();
    }

    public CicadaException(StatusEnum statusEnum, String message) {
        super(message);
        this.errorMessage = message;
        this.errorCode = statusEnum.getCode();
    }

    public CicadaException(Exception oriEx) {
        super(oriEx);
    }

    public CicadaException(Throwable oriEx) {
        super(oriEx);
    }

    public CicadaException(String message, Exception oriEx) {
        super(message, oriEx);
        this.errorMessage = message;
    }

    public CicadaException(String message, Throwable oriEx) {
        super(message, oriEx);
        this.errorMessage = message;
    }

}
