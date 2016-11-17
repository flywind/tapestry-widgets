package org.flywind.widgets.test.dao.example;

import java.util.List;

import org.flywind.widgets.test.dao.base.FBaseDao;
import org.flywind.widgets.test.entities.example.Item;

public interface ItemDao extends FBaseDao<Item>{

	public List<Item> getAllItemsByParentId(Long parentId);
}
