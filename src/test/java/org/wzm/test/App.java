package org.wzm.test;

import org.wzm.simple.ioc.context.Context;
import org.wzm.simple.ioc.context.DefaultContext;
import org.wzm.test.service.ItemService;

public class App {
    public static void main(String[] args) {
        Context context = new DefaultContext("org.wzm.test");
        ItemService itemService = (ItemService) context.getBean("itemServiceImpl");
        System.out.println("1111" + itemService.getItemInfo(100));
    }
}
