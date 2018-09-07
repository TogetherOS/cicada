package top.crossoverjie.cicada.server.util;

import org.slf4j.Logger;
import top.crossoverjie.cicada.server.annotation.CicadaAction;
import top.crossoverjie.cicada.server.annotation.CicadaConfig;
import top.crossoverjie.cicada.server.annotation.Interceptor;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Function: package Scanner
 *
 * @author crossoverJie
 *         Date: 2018/9/1 11:36
 * @since JDK 1.8
 */
public class ClassScanner {

    private final static Logger LOGGER = LoggerBuilder.getLogger(ClassScanner.class);


    private static Map<String, Class<?>> actionMap = null;
    private static Map<String, Class<?>> interceptorMap = null;
    private static Map<String, Class<?>> configurationMap = null;

    /**
     * get @CicadaAction
     *
     * @param packageName
     * @return
     * @throws Exception
     */
    public static Map<String, Class<?>> getCicadaAction(String packageName) throws Exception {

        if (actionMap == null) {
            Set<Class<?>> clsList = getClasses(packageName);

            if (clsList == null || clsList.isEmpty()) {
                return actionMap;
            }

            actionMap = new HashMap<>(16);
            for (Class<?> cls : clsList) {

                if (cls.getAnnotation(CicadaAction.class) == null) {
                    continue;
                }

                Annotation[] annotations = cls.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (!(annotation instanceof CicadaAction)) {
                        continue;
                    }
                    CicadaAction cicadaAction = (CicadaAction) annotation;
                    actionMap.put(cicadaAction.value() == null ? cls.getName() : cicadaAction.value(), cls);
                }

            }
        }
        return actionMap;
    }

    /**
     * get @CicadaAction
     *
     * @param packageName
     * @return
     * @throws Exception
     */
    public static Map<String, Class<?>> getCicadaInterceptor(String packageName) throws Exception {

        if (interceptorMap == null) {
            Set<Class<?>> clsList = getClasses(packageName);

            if (clsList == null || clsList.isEmpty()) {
                return interceptorMap;
            }

            interceptorMap = new HashMap<>(16);
            for (Class<?> cls : clsList) {

                if (cls.getAnnotation(Interceptor.class) == null) {
                    continue;
                }

                Annotation[] annotations = cls.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (!(annotation instanceof Interceptor)) {
                        continue;
                    }
                    Interceptor interceptor = (Interceptor) annotation;
                    interceptorMap.put(interceptor.value() == null ? cls.getName() : interceptor.value(), cls);
                }

            }
        }

        return interceptorMap;
    }

    /**
     * @Author liwenguang
     * @Date 2018/9/7 上午2:39
     * @Description 获取配置注解
     */
    public static Map<String, Class<?>> getCicadaConfig(String packageName) throws Exception {

        if (configurationMap == null) {
            Set<Class<?>> clsList = getClasses(packageName);

            if (clsList == null || clsList.isEmpty()) {
                return configurationMap;
            }

            configurationMap = new HashMap<>(8);
            for (Class<?> cls : clsList) {

                if (cls.getAnnotation(CicadaConfig.class) == null) {
                    continue;
                }

                Annotation[] annotations = cls.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (!(annotation instanceof CicadaConfig)) {
                        continue;
                    }
                    CicadaConfig configuration= (CicadaConfig) annotation;
                    configurationMap.put(configuration.prefix() == null ? cls.getName() : configuration.prefix(), cls);
                }

            }
        }

        return configurationMap;
    }

    /**
     * get All classes
     *
     * @param packageName
     * @return
     * @throws Exception
     */
    public static Set<Class<?>> getClasses(String packageName) throws Exception {

        Set<Class<?>> classes = new HashSet<>();
        boolean recursive = true;

        String packageDirName = packageName.replace('.', '/');

        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    JarFile jar;
                    try {
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        Enumeration<JarEntry> entries = jar.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            if (name.charAt(0) == '/') {
                                name = name.substring(1);
                            }
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                if (idx != -1) {
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                if ((idx != -1) || recursive) {
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            classes.add(Class.forName(packageName + '.' + className));
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        LOGGER.error("IOException", e);
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("IOException", e);
        }

        return classes;
    }


    public static void findAndAddClassesInPackageByFile(String packageName,
                                                        String packagePath, final boolean recursive, Set<Class<?>> classes) {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] files = dir.listFiles(file -> (recursive && file.isDirectory())
                || (file.getName().endsWith(".class")));
        for (File file : files) {
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "."
                                + file.getName(), file.getAbsolutePath(), recursive,
                        classes);
            } else {
                String className = file.getName().substring(0,
                        file.getName().length() - 6);
                try {
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    LOGGER.error("ClassNotFoundException", e);
                }
            }
        }
    }

}
