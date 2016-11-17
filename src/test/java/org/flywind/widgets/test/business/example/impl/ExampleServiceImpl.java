package org.flywind.widgets.test.business.example.impl;

import java.util.List;
import java.util.Map;

import org.flywind.widgets.core.dao.FPage;
import org.flywind.widgets.test.business.example.ExampleService;
import org.flywind.widgets.test.common.result.Grid;
import org.flywind.widgets.test.dao.example.ExampleDao;
import org.flywind.widgets.test.dao.example.ItemDao;
import org.flywind.widgets.test.entities.example.Example;
import org.flywind.widgets.test.entities.example.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>ExampleService接口实现方法</p>
 * 
 * @author flywind(飞风)
 * @date 2015年9月18日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Service
public class ExampleServiceImpl implements ExampleService {

	@Autowired
	private ExampleDao exampleDao;
	
	@Autowired
	private ItemDao itemDao;
	
	public void saveOrUpdate(Example o){
		exampleDao.save(o);
	}
	
	public void delete(Example o){
		exampleDao.delete(o);
	}
	
	public Example getById(Long id){
		return exampleDao.getById(Example.class, id);
	}
	
	public List<Example> getAllExamples(){
		return exampleDao.getAllExamples();
	}
	
	public List<Example> getAllExamples(String customerCode, FPage page){
		return exampleDao.getAllExamples(customerCode, page);
	}
	
	public List<Example> getAllExamples(Map<String,Object> map, FPage page){
		return exampleDao.getAllExamples(map, page);
	}
	
	public Grid getAllExanplesToJson(String customerCode, FPage page){
		Grid grid = new Grid();
		List<Example> examples = exampleDao.getAllExamples(customerCode, page);
		
		Long total = exampleDao.getAllExamplesCount(customerCode);
		grid.setRows(examples);
		grid.setTotal(total);
		
		return grid;
	}
	
	public List<Item> getItemsByExampleId(Long exampleId){
		return itemDao.getAllItemsByParentId(exampleId);
	}
	
	public List<Example> getAllExampleByTree(Example example, FPage page, String customerCode){
		return exampleDao.getAllExampleByTree(example, page, customerCode);
	}
	
}
