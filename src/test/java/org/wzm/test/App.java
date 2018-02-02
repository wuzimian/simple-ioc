package org.wzm.test;

import org.wzm.simple.ioc.context.ApplicationContext;
import org.wzm.simple.ioc.context.DefaultApplicationContext;
import org.wzm.test.service.ItemService;

public class App {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new DefaultApplicationContext("org.wzm.test");
        ItemService itemService = (ItemService) applicationContext.getBean("itemServiceImpl");
        System.out.println("1111" + itemService.getItemInfo(100));
    }
}
