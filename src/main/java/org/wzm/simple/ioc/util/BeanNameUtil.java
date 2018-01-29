package org.wzm.simple.ioc.util;

public class BeanNameUtil {
    /**
     *
     * @param beanName
     * @return 第一个字母小写
     */
    public static String normalize(String beanName) {
        char [] chars = beanName.toCharArray();
        if(chars[0] >= 'A' && chars[0] <= 'Z') {
            chars[0] = (char)(chars[0] + ('a' - 'A'));
        }
        return String.valueOf(chars);
    }
}
