package org.flywind.widgets.test.pages;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.flywind.widgets.WidgetSymbolConstants;
import org.flywind.widgets.core.dao.FPage;
import org.flywind.widgets.test.base.AppBase;
import org.flywind.widgets.test.business.example.ExampleService;
import org.flywind.widgets.test.entities.example.Example;

@Import(stylesheet="context:assets/styles/demo.css")
public class FPaginateTest extends AppBase {

	@Inject
	private ExampleService exampleService;
	
	@Property
	private List<Example> examples;
	
	@Property
	private Example example;
	
	@InjectComponent
	private Zone meZone,formZone;
	
	@InjectComponent
	private Form createForm;
	
	public void onPrepareForRender(){
		if(example == null){
			example = new Example();
		}
	}
	
	@OnEvent(component="glist",value=WidgetSymbolConstants.PAGER_LOAD_DATA)
	public void uconTest(){

		FPage page = (FPage)request.getAttribute("page");
		String userName = (String)request.getAttribute("userName");
		String creater = (String)request.getAttribute("creater");
		
		if(example == null){
			example = new Example();
		}
		
		if(StringUtils.isNotEmpty(userName)){
			example.setUserName(userName);
		}
		if(StringUtils.isNotEmpty(creater)){
			example.setCreater(creater);
		}
		examples = findExamplesData(example,page);
		if(request.isXHR()){
			ajaxResponseRenderer.addRender(meZone);
		}
		
	}
	
	public List<Example> findExamplesData(Example example, FPage page){
		String customerCode = "0755";
		return exampleService.getAllExampleByTree(example,page,customerCode);
	}
	
	public void onPrepareForSubmit(){
		if(example == null){
			example = new Example();
		}
	}
	
	public void onSuccessFromCreateForm(){
		if(request.isXHR()){
			ajaxResponseRenderer.addRender(formZone);
		}
	}
	
}
