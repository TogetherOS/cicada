package top.crossoverjie.cicada.server.reflect;

import org.junit.Test;

import java.util.Set;

public class ClassScannerTest {
    @Test
    public void getCicadaClasses() throws Exception {
        Set<Class<?>> classes = ClassScanner.getClasses("top.crossoverjie.cicada");
        System.out.println(classes.size());
    }

}