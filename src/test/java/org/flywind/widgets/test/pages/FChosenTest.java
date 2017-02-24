package org.flywind.widgets.test.pages;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

@Import(stylesheet="context:assets/styles/demo.css")
public class FChosenTest {

	@Property
	@Persist(PersistenceConstants.FLASH)
	private String tags;
	
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String tags2;
	
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String name,name2;
	
	@InjectComponent("meform")
	private Form meform;
	
	@InjectComponent("tags")
	private TextField tagsField;
	
	@InjectComponent
	private Zone myZone;
	
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String initValus;
	
	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;
	
	public void setupRender(){
		tags = "4,1,2";
	}
	
	public void onValidateFromMeform(){
		System.out.println(tags);
		if(tags == null){
			meform.recordError(tagsField, "Please select at least one tag!");
		}
		ajaxResponseRenderer.addRender(myZone);
	}
	
	public void onSuccessFromMeform(){
		System.out.println(tags);	
	}
	
	public void onValidateFromMyform(){
		System.out.println(tags2);
	}
}
