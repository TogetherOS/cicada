package top.crossoverjie.cicada.server.util;

import org.slf4j.Logger;
import top.crossoverjie.cicada.server.annotation.CicadaAction;
import top.crossoverjie.cicada.server.annotation.Interceptor;

import java.io.File;
import java.io.FileFilter;
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
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中，以下俩种方法都可以
                    //网上的第一种方法，
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                    //网上的第二种方法
                    //addClass(classes,filePath,packageName);
                } else if ("jar".equals(protocol)) {
                    // 如果是jar包文件
                    // 定义一个JarFile
                    JarFile jar;
                    try {
                        // 获取jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        // 从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        // 同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以/开头的
                            if (name.charAt(0) == '/') {
                                // 获取后面的字符串
                                name = name.substring(1);
                            }
                            // 如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                // 如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive) {
                                    // 如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        // 去掉后面的".class" 获取真正的类名
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            // 添加到classes
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
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            @Override
            public boolean accept(File file) {
                return (recursive && file.isDirectory())
                        || (file.getName().endsWith(".class"));
            }
        });
        // 循环所有文件
        for (File file : dirfiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "."
                                + file.getName(), file.getAbsolutePath(), recursive,
                        classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
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
