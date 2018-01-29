package org.wzm.simple.ioc.definition.reader;

import org.wzm.simple.ioc.annotation.Component;
import org.wzm.simple.ioc.definition.BeanDefinition;
import org.wzm.simple.ioc.factory.BeanFactory;
import org.wzm.simple.ioc.util.BeanNameUtil;
import org.wzm.simple.ioc.util.ReflectionUtil;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultBeanDefinitionReader implements BeanDefinitionReader {
    private static final char DOT_CHAR = '.';
    private static final String DOT_CHAR_SPLITTER = "\\.";
    private static final char SLASH_CHAR = '/';
    private static final String CLASS_SUFFIX = ".class";

    private final String basePackage;

    public DefaultBeanDefinitionReader(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void resolve(BeanFactory beanFactory) {
        Map<String, BeanDefinition> beanDefinitionMap = scan(basePackage);
        for (Map.Entry<String, BeanDefinition> e : beanDefinitionMap.entrySet()) {
            beanFactory.registerBeanDefinition(e.getKey(), e.getValue());
        }
    }

    public Map<String, BeanDefinition> scan(String beanPackage) {
        Map<String, BeanDefinition> beanMap = new HashMap<>();
        String packageDirectory = beanPackage.replace(DOT_CHAR, SLASH_CHAR);
        try {
            Enumeration<URL> directories = Thread.currentThread().getContextClassLoader().getResources(packageDirectory);
            List<Class<?>> classes = new ArrayList<>();
            while (directories.hasMoreElements()) {
                URL url = directories.nextElement();
                File file = new File(url.getFile());
                classes.addAll(findClassesRecursively(beanPackage, file));
            }
            List<Class<?>> components = getComponentBeans(classes);
            beanMap.putAll(extractBeanDefinitions(components));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return beanMap;
    }

    private List<Class<?>> findClassesRecursively(String packageName, File file) {
        List<Class<?>> classes = new ArrayList<>();
        File[] files = file.listFiles();
        for (File subFile : files) {
            if (isClassFile(subFile)) {
                Class<?> clazz = ReflectionUtil.getClass(packageName + DOT_CHAR + removeClassSuffix(subFile.getName()));
                classes.add(clazz);
            } else {
                classes.addAll(findClassesRecursively(packageName + DOT_CHAR + subFile.getName(), subFile));
            }
        }
        return classes;
    }

    private boolean isClassFile(File file) {
        return (file.isFile() && file.getName().endsWith(CLASS_SUFFIX)) ? true : false;
    }

    private Map<String, BeanDefinition> extractBeanDefinitions(List<Class<?>> classNames) {
        Map<String, BeanDefinition> beanMap = new HashMap<>();
        for (Class<?> className : classNames) {
            BeanDefinition beanDefinition = getBeanDefinition(className);
            if (beanDefinition != null) {
                beanMap.put(beanDefinition.getName(), beanDefinition);
            }
        }
        return beanMap;
    }

    private List<Class<?>> getComponentBeans(List<Class<?>> classes){
        return classes.stream().filter(x -> hasComponentAnnotation(x)).collect(Collectors.toList());
    }

    private boolean hasComponentAnnotation(Class<?> clazz) {
        return clazz != null && clazz.isAnnotationPresent(Component.class);
    }

    private BeanDefinition getBeanDefinition(Class<?> clazz) {
        String beanName = clazz.getAnnotation(Component.class).value();
        beanName = beanName.isEmpty() ? BeanNameUtil.normalize(getShortClassName(clazz.getName())) : beanName;
        return new BeanDefinition(beanName, clazz.getName());
    }

    private String getShortClassName(String className) {
        String[] splits = className.split(DOT_CHAR_SPLITTER);
        return splits[splits.length - 1];
    }

    private String removeClassSuffix(String className) {
        return className.substring(0, className.lastIndexOf(DOT_CHAR));
    }
}
