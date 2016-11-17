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
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.flywind.widgets.WidgetSymbolConstants;
import org.flywind.widgets.core.dao.FPage;
import org.flywind.widgets.test.base.AppBase;
import org.flywind.widgets.test.business.example.ExampleService;
import org.flywind.widgets.test.entities.example.Example;
import org.flywind.widgets.test.entities.example.GridHeader;

public class FDatagridTest3 extends AppBase {
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	@Inject
	private ExampleService exampleService;
	
	@Property
	private List<Example> examples;
		
	@InjectComponent
	private Zone zoneOne,zoneTwo;
	
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
	
	public void onPrepare(){
		
		getGridHeaderData();
	}
	
	public void getGridHeaderData(){
		//gridHeaders = gridHeaderService.getGridHeader("Example", "0755");
		p = new HashMap<String,Object>();
		for(GridHeader h : gridHeaders){
			String language = request.getLocale().toString();
			if("zh_CN".equalsIgnoreCase(language)){
				p.put(h.getField(), h.getTitleCn());
			}else if("en".equalsIgnoreCase(language)){
				p.put(h.getField(), h.getTitleEn());
			}
			
		}
		userNameLabel = (String)p.get("userName");
	}
	
	public void setupRender(){
		System.out.println(request.getLocale());
		paramsMap = null;
		delUrl = componentResources.createEventLink("del").toURI();
		deleteUrl = componentResources.createEventLink("delete").toURI();
		
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
		javaScriptSupport.require("init-test").invoke("test").with(link);
	}
	
	void onSearch(Long id){
		System.out.println(id);
	}

	
	public void onUpdataField(){
		String tableName = request.getParameter("tableName");
		String fieldValue = request.getParameter("fieldValue");
		String field = request.getParameter("field");
		String customerCode = request.getParameter("customerCode");
		/*GridHeader gh = gridHeaderService.getGridHeader(tableName, field, customerCode);
		if(gh != null){
			gh.setTitleCn(fieldValue);
			gridHeaderService.update(gh);
		}*/
		getGridHeaderData();
		ajaxResponseRenderer.addRender(zoneOne);
		System.out.println(tableName+fieldValue+field+customerCode);
	}
	
}
