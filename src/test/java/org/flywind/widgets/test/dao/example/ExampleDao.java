package org.flywind.widgets.test.dao.example;

import java.util.List;
import java.util.Map;

import org.flywind.widgets.core.dao.FPage;
import org.flywind.widgets.test.dao.base.FBaseDao;
import org.flywind.widgets.test.entities.example.Example;


/**
 * <p>ExampleDao接口类</p>
 * 
 * @author flywind(飞风)
 * @date 2015年9月18日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface ExampleDao extends FBaseDao<Example> {

	public List<Example> getAllExamples();
	
	public List<Example> getAllExamples(String customerCode, FPage page);
	
	public List<Example> getAllExamples(Map<String,Object> map, FPage page);
	
	public Long getAllExamplesCount(String customerCode);
	
	public List<Example> getAllExampleByTree(Example example, FPage page, String customerCode);
}
