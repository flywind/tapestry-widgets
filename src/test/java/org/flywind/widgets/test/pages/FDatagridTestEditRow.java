package org.flywind.widgets.test.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.URLEncoder;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.flywind.widgets.WidgetSymbolConstants;
import org.flywind.widgets.core.dao.FPage;
import org.flywind.widgets.test.base.AppBase;
import org.flywind.widgets.test.business.example.ExampleService;
import org.flywind.widgets.test.common.result.Json;
import org.flywind.widgets.test.entities.example.Example;
import org.flywind.widgets.test.entities.example.GridHeader;
import org.flywind.widgets.test.utils.Base64;

import com.alibaba.fastjson.JSON;

public class FDatagridTestEditRow extends AppBase {
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	@Inject
	private ExampleService exampleService;
	
	@Property
	private List<Example> examples;
		
	@InjectComponent
	private Zone zoneOne;
	
	@Property
	private String userName,customerCode;
	
	@Property
	@Persist
	private Map<String,Object> paramsMap;
	
	@Property
	private String delUrl,deleteUrl;
	
	@Property
	private GridHeader gridHeader;
	
	@Property
	private String userNameLabel;
	
	@Property
	private List<GridHeader> gridHeaders;
	
	@Property
	Map<String,Object> p;
	
	@Property
	private String userNameLabelValue;
	
	@Property
	private String cbox;
	
	public void setupRender(){
		System.out.println(request.getLocale());
		paramsMap = null;
		delUrl = componentResources.createEventLink("del").toURI();
		deleteUrl = componentResources.createEventLink("delete").toURI();
		
		cbox = componentResources.createEventLink("cbox").toAbsoluteURI();
		
	}
	
	public void onSuccess(){
		paramsMap = null;
		if(paramsMap == null){
			paramsMap = new HashMap<String,Object>();
			paramsMap.put("userName", userName);
			paramsMap.put("customerCode", "0755");
			
		}
		if(request.isXHR()){
			ajaxResponseRenderer.addRender(zoneOne);
		}
	}
	
	
	@OnEvent(component="newContracts", value=WidgetSymbolConstants.EASYUI_DATAGRID_LOAD_DATA)
	public void filterExamplesData(){
		//sleep(3000);
		if(paramsMap == null){
			paramsMap = new HashMap<String,Object>();
			paramsMap.put("customerCode", "0755");
		}
		examples = exampleService.getAllExamples(paramsMap,(FPage)request.getAttribute("page"));
		System.out.println(examples.size());
	}
	
	public void sleep(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//@OnEvent(value="del")
	public void onDel(int id){
		System.out.println(id);
		ajaxResponseRenderer.addRender(zoneOne);
	}
	
	public void onDelete(List<?> o){
		System.out.println(o);
	}
	
	
	
	public void afterRender(){
		String link = componentResources.createEventLink("search").toAbsoluteURI();
		String save = componentResources.createEventLink("save").toAbsoluteURI();
		JSONObject spec = new JSONObject();
		spec.put("link", link);
		spec.put("save", save);
		javaScriptSupport.require("init-test").invoke("test").with(spec);
	}
	
	void onSearch(Long id){
		System.out.println(id);
	}
	
	@Inject
	private URLEncoder uRLEncoder;
	
	void onSave(){
		
		//request.getHeader(name)
		String data = (String)request.getParameter("selectData"); 
		byte[] enddata = Base64.decode(data);
		String s = new String(enddata);
		System.out.println(s);
		JSONObject j = new JSONObject(s);
		//String endDateStr=enddata.toString();
		//String newdata = data.replaceAll("\", "");
		//com.alibaba.fastjson.JSONObject json = JSON.parseObject(data);
		//com.alibaba.fastjson.JSONArray arr = (com.alibaba.fastjson.JSONArray) json.get("infoList");
		System.out.println("++++++++++++++"+data);
		try {
			String o = null;
			o.length();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public JSONArray onCbox(){
		JSONObject obj = new JSONObject();
		obj.put("companyId", "1");
		obj.put("companyName", "中兴科技");
		
		JSONObject obj2 = new JSONObject();
		obj2.put("companyId", "2");
		obj2.put("companyName", "华为技术");
		
		JSONArray arr = new JSONArray();
		arr.put(obj);
		arr.put(obj2);
		
		return arr;
	}
	
}
