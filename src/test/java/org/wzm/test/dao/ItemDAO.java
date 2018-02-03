package org.wzm.test.dao;

import org.wzm.simple.ioc.annotation.Component;
import org.wzm.test.common.Item;

@Component
public class ItemDAO{
    public Item getItem(long id){
        return new Item(id, "item"+id);
    }
}
