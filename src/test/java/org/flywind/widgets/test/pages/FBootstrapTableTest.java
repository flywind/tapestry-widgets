package org.flywind.widgets.test.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.flywind.widgets.WidgetSymbolConstants;
import org.flywind.widgets.core.dao.FPage;
import org.flywind.widgets.test.base.AppBase;
import org.flywind.widgets.test.business.example.ExampleService;
import org.flywind.widgets.test.entities.example.Example;

@Import(stylesheet="context:assets/styles/demo.css")
public class FBootstrapTableTest extends AppBase  {

	@Inject
	private ExampleService exampleService;
	
	@Property
	@Persist
	private Map<String,Object> paramsMap;
	
	@Property
	private List<Example> examples;
	
	@InjectComponent
	private Zone zoneOne;
	
	@Property
	private String delUrl,deleteUrl;
	
	@Inject
	protected Locale currentLocale;
	
	@Property
	@Persist
	private String lang;
	
	public void setupRender(){
		lang = currentLocale.toLanguageTag();
		delUrl = componentResources.createEventLink("del").toURI();
	}
	
	
	@OnEvent(component="t1", value=WidgetSymbolConstants.BOOTSTRAP_TABLE_LOAD_DATA)
	public void filterExamplesData(){
		if(paramsMap == null){
			paramsMap = new HashMap<String,Object>();
			paramsMap.put("customerCode", "0755");
		}
		examples = exampleService.getAllExamples(paramsMap,(FPage)request.getAttribute("page"));
		System.out.println(examples.size());
	}
	
	public void onDel(List<String> rc){
		
		for(String i : rc){
			System.out.println(i);
		}
	}
}
