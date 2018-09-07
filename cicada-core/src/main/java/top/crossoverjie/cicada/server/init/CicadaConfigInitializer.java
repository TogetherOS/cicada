package top.crossoverjie.cicada.server.init;

import top.crossoverjie.cicada.server.CicadaServer;
import top.crossoverjie.cicada.server.config.AppConfig;
import top.crossoverjie.cicada.server.exception.CicadaException;
import top.crossoverjie.cicada.server.resources.YamlResource;
import top.crossoverjie.cicada.server.util.ClassScanner;
import top.crossoverjie.cicada.server.util.PathUtil;
import top.crossoverjie.cicada.server.util.PropertiesUtil;

import java.util.Map;
import java.util.Properties;

/**
 * 配置文件初始化
 * 1. 获取所有的配置文件的流一次性解析为 key:val
 * 2. 根据key 与 配置文件的 prefix 一一对应进行属性赋值
 * liwenguang 2018-09-07 13:25:55
 */
public class CicadaConfigInitializer {

    private static final String APPLICATION_URL = "application";

    public static void main(String[] args) {
        init(CicadaConfigInitializer.class, args);
    }

    public static void init(Class<?> clazz, String[] args) {
        try {
            //init application
            AppConfig.getInstance().setRootPackageName(CicadaServer.class.getPackage().getName());
            Map<String, Class<?>> cicadaConfig = ClassScanner.getCicadaConfig(AppConfig.getInstance().getRootPackageName());
            for (Map.Entry<String, Class<?>> classEntry : cicadaConfig.entrySet()) {
                String name = classEntry.getValue().getName();
                if (name.equals(AppConfig.class.getName())) {
                    Properties properties = YamlResource.getInstance().file2Properties(APPLICATION_URL + ".yml");
                    if (properties.isEmpty()) {
                        continue;
                    }
                    // TODO 使用反射 field 赋值，或者反射执行 set 方法赋值，这样可以批量设置
                    AppConfig.getInstance().setPort(PropertiesUtil.getInteger(properties, classEntry.getKey() + ".port"));
                    AppConfig.getInstance().setRootPath(
                            PathUtil.addStartSplit(PropertiesUtil.getString(properties, classEntry.getKey() + ".path", "")));
                } else {
                    // 用户自定义配置
                    String path = clazz.getPackage().getName();
                }
            }
            // TODO args 命令参数解析，port，path
        } catch (Exception e) {
            e.printStackTrace();
            throw new CicadaException(e, "103", "配置文件读取错误");
        }
    }
}
