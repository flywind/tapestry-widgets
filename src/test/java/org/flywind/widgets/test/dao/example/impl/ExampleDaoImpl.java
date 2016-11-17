package org.flywind.widgets.test.dao.example.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.flywind.widgets.core.dao.FPage;
import org.flywind.widgets.test.dao.base.AbstractFBaseDao;
import org.flywind.widgets.test.dao.example.ExampleDao;
import org.flywind.widgets.test.entities.example.Example;
import org.flywind.widgets.utils.JQueryUtils;
import org.springframework.stereotype.Repository;

/**
 * <p>exampleDao实现方法</p>
 * 
 * @author flywind(飞风)
 * @date 2015年9月18日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Repository
public class ExampleDaoImpl extends AbstractFBaseDao<Example> implements ExampleDao {

	public List<Example> getAllExamples(){
		String hql = "from Example";
		System.out.println(this.query(hql));
		return this.query(hql);
	}
	
	public List<Example> getAllExamples(String customerCode, FPage page){
		String countHql = "select count(t.id) from Example t where customerCode=:customerCode";
		Map<String,Object> paramsc = new HashMap<String, Object>();
		paramsc.put("customerCode", customerCode);
		Long total = this.count(countHql, paramsc);
		page.setRowCount(total.intValue()); 
		
		String hql = "from Example where customerCode=:customerCode";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("customerCode", customerCode);
		return this.query(hql, params, page.getPageNumber(), page.getPageSize());
	}
	
	public List<Example> getAllExamples(Map<String,Object> map, FPage page){
		String customerCode = (String)map.get("customerCode");
		String userName = (String)map.get("userName");
		
		String countHql = "select count(t.id) from Example t where customerCode=:customerCode";
		String hql = "from Example where customerCode=:customerCode";
		Map<String,Object> paramsc = new HashMap<String, Object>();
		Map<String,Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(userName)){
			hql += " and userName like:userName";
			countHql += " and userName like:userName";
			params.put("userName", "%"+userName+"%");
		}
		
		paramsc.put("customerCode", customerCode);
		Long total = this.count(countHql, paramsc);
		page.setRowCount(total.intValue()); 
		
		params.put("customerCode", customerCode);
		
		int totalPages = JQueryUtils.findTotalPages(total.intValue(), page.getPageNumber());
		page.setPageCount(totalPages);
		
		return this.query(hql, params, page.getPageNumber(), page.getPageSize());
	}
	
	public Long getAllExamplesCount(String customerCode){
		String countHql = "select count(t.id) from Example t where customerCode=:customerCode";
		Map<String,Object> paramsc = new HashMap<String, Object>();
		paramsc.put("customerCode", customerCode);
		return this.count(countHql, paramsc);
	}
	
	public List<Example> getAllExampleByTree(Example example, FPage page, String customerCode){
		
		String countHql = "select count(t.id) from Example t where customerCode=:customerCode";
		String hql = "from Example where customerCode=:customerCode";
		Map<String,Object> paramsc = new HashMap<String, Object>();
		Map<String,Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(example.getUserName())){
			hql += " and userName like:userName";
			params.put("userName", "%"+example.getUserName()+"%");
			countHql += " and userName like:userName";
			paramsc.put("userName", "%"+example.getUserName()+"%");
			
		}
		if(StringUtils.isNotEmpty(example.getCreater())){
			hql += " and creater like:creater";
			params.put("creater", "%"+example.getCreater()+"%");
			countHql += " and creater like:creater";
			paramsc.put("creater", "%"+example.getCreater()+"%");
			
		}
		
		paramsc.put("customerCode", customerCode);
		Long total = this.count(countHql, paramsc);
		page.setRowCount(total.intValue()); 
		
		params.put("customerCode", customerCode);
		
		int totalPages = JQueryUtils.findTotalPages(total.intValue(), page.getPageSize());
		page.setPageCount(totalPages);
		
		return this.query(hql, params, page.getPageNumber(), page.getPageSize());
	}
}
