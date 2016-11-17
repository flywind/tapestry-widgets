package org.flywind.widgets.test.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
@Import(stylesheet="context:assets/styles/demo.css")
public class FDialogTest {

	@Property
	private int count;
	
	@InjectComponent
	private Zone myzone;
	
	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;
	
	public void setupRender(){
		count = 0;
	}
	
	@OnEvent(component="z1", value = EventConstants.ACTION)
	public void updataCount(int count){
		this.count += count;
		ajaxResponseRenderer.addRender(myzone);
	}
	
	@OnEvent(component="z2",value = EventConstants.ACTION)
	public void updataCount2(int count){
		this.count = count+8;
		ajaxResponseRenderer.addRender(myzone);
	}
	
	public JSONObject getOptions(){
		return new JSONObject("draggable", false);
	}
}
