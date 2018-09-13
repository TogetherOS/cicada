package top.crossoverjie.cicada.server.configuration;

import top.crossoverjie.cicada.server.constant.CicadaConstant;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/9/8 16:28
 * @since JDK 1.8
 */
public class ApplicationConfiguration extends AbstractCicadaConfiguration {

    public ApplicationConfiguration() {
        super.setPropertiesName(CicadaConstant.SystemProperties.APPLICATION_PROPERTIES);
    }


}
