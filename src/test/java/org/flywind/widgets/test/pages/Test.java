package org.flywind.widgets.test.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.flywind.widgets.test.base.AppBase;

public class Test extends AppBase {

	@Property
	@Persist(PersistenceConstants.FLASH)
	private String name;
	
	@InjectComponent("myform")
	private Form myform;
	
	@InjectComponent
	private Zone mezone;
	
	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;
	
	public void onValidateFromMyform(){
		if(myform.getHasErrors()){
			return;
		}
	}
	
	public void onSuccessFromMyform(){
		System.out.println(name);
		ajaxResponseRenderer.addRender(mezone);
	}
}
