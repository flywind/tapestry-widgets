package org.flywind.widgets.test.pages;

import java.util.List;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.flywind.widgets.WidgetSymbolConstants;
import org.flywind.widgets.core.dao.FPage;
import org.flywind.widgets.test.base.AppBase;
import org.flywind.widgets.test.business.example.ExampleService;
import org.flywind.widgets.test.entities.example.Example;
import org.flywind.widgets.test.entities.example.Item;
import org.flywind.widgets.utils.JQueryUtils;


@Import(stylesheet="context:assets/styles/demo.css")
public class FSubgridTest extends AppBase {
	
	@Inject
	private ExampleService exampleService;
	
	@Property
	private List<Example> examples;
	
	@Property
	private List<Item> items;
		
	@Property
	private String url,delUrl;
	
	@InjectComponent
	private Zone zoneOne;
	
	@OnEvent(component="newContracts2", value=WidgetSymbolConstants.EASYUI_SUBGRID_LOAD_DATA)
	public void filterExamplesData2(){
		String customerCode = "0755";
		examples = exampleService.getAllExamples(customerCode,(FPage)request.getAttribute("page"));
	}

	public void setupRender(){
		url = componentResources.createEventLink("show").toAbsoluteURI();
		delUrl = componentResources.createEventLink("del").toAbsoluteURI();
	}
	
	public JSONObject onShow(Long id){
		items = exampleService.getItemsByExampleId(id);  
		return JQueryUtils.toGridJson(items, items.size());
	}
	
	public void onDel(Long id){
		System.out.println(id);
	}
	
	public JSONArray getOpts(){
		
		JSONObject nj = new JSONObject();
		nj.put("field", "id");
		nj.put("title", "ID");
		nj.put("width", 50);
		
		JSONObject uj = new JSONObject();
		uj.put("field", "userName");
		uj.put("title", "User Name");
		uj.put("width", 100);
		
		JSONObject cj = new JSONObject();
		cj.put("field", "creater");
		cj.put("title", "Creater");
		cj.put("width", 300);
		
		JSONArray d = new JSONArray();
		d.put(nj);
		d.put(uj);
		d.put(cj);
		
		return d;
	}

}
