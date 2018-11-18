package top.crossoverjie.cicada.base.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/11/18 22:43
 * @since JDK 1.8
 */
public class HighlightingCompositeConverterEx extends ForegroundCompositeConverterBase<ILoggingEvent> {

    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        Level level = event.getLevel();
        switch (level.toInt()) {
            case Level.ERROR_INT:
                return ANSIConstants.BOLD + ANSIConstants.RED_FG;
            case Level.WARN_INT:
                return ANSIConstants.RED_FG;
            case Level.DEBUG_INT:
                return ANSIConstants.BLUE_FG;
            case Level.INFO_INT:
                return ANSIConstants.BOLD + ANSIConstants.GREEN_FG;
            default:
                return ANSIConstants.DEFAULT_FG;
        }
    }

}
