package org.flywind.widgets.test.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.annotations.Import;
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
@Import(stylesheet="context:assets/styles/demo.css")
public class FDatagridTest extends AppBase {
	
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
	
	
	public void setupRender(){
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
	
	@OnEvent(component="newContracts2", value=WidgetSymbolConstants.EASYUI_DATAGRID_LOAD_DATA)
	public void filterExamplesData2(){
		String customerCode = "0755";
		examples = exampleService.getAllExamples(customerCode,(FPage)request.getAttribute("page"));
	}
	
	@OnEvent(component="newContracts3", value=WidgetSymbolConstants.EASYUI_DATAGRID_LOAD_DATA)
	public void filterExamplesData3(){
		String customerCode = "0755";
		examples = exampleService.getAllExamples(customerCode,(FPage)request.getAttribute("page"));
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
		//javaScriptSupport.require("init-fDatagridTest").invoke("init");
	}

}
