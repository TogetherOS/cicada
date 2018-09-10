package top.crossoverjie.cicada.example.configuration;

import top.crossoverjie.cicada.server.configuration.AbstractCicadaConfiguration;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/9/8 16:28
 * @since JDK 1.8
 */
public class KafkaConfiguration extends AbstractCicadaConfiguration {

    public KafkaConfiguration() {
        super.setPropertiesName("kafka.properties");
    }


}
