package org.flywind.widgets.test.business.example;

import java.util.List;
import java.util.Map;

import org.flywind.widgets.core.dao.FPage;
import org.flywind.widgets.test.common.result.Grid;
import org.flywind.widgets.test.entities.example.Example;
import org.flywind.widgets.test.entities.example.Item;

/**
 * <p>ExampleService接口</p>
 * 
 * @author flywind(飞风)
 * @date 2015年9月18日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface ExampleService {

	public void saveOrUpdate(Example o);
	
	public void delete(Example o);
	
	public Example getById(Long id);
	
	public List<Example> getAllExamples();
	
	public List<Example> getAllExamples(String customerCode, FPage page);
	
	public List<Example> getAllExamples(Map<String,Object> map, FPage page);
	
	public Grid getAllExanplesToJson(String customerCode, FPage page);
	
	public List<Item> getItemsByExampleId(Long exampleId);
	
	public List<Example> getAllExampleByTree(Example example, FPage page, String customerCode);
}
