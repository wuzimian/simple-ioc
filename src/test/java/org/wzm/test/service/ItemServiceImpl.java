package org.wzm.test.service;

import org.wzm.simple.ioc.annotation.Component;
import org.wzm.simple.ioc.annotation.Inject;
import org.wzm.test.common.Item;
import org.wzm.test.dao.ItemDAO;

@Component
public class ItemServiceImpl implements ItemService {
    @Inject
    private ItemDAO itemDAO;

    @Override
    public Item getItemInfo(long itemId) {
        return itemDAO.getItem(itemId);
    }
}
