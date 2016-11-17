package org.flywind.widgets.test.pages;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.flywind.widgets.test.base.AppBase;

@Import(stylesheet="context:assets/styles/demo.css")
public class FDialogCloseTest extends AppBase {

	@Property
	@Persist
	private String name;
	
	@Property
	@Persist
	private String name2;
	
	@InjectComponent
	private Zone mezone;
	
	/*void onSuccessFromForm1(){
		System.out.println("successs");
		
	}
	
	void onCancel(){
		System.out.println("cancel");
	}*/
	
	void onCancel2(){
		ajaxResponseRenderer.addRender(mezone);
		System.out.println("cancel2");
	}
	
	void onSuccessFromForm2(){
		ajaxResponseRenderer.addRender(mezone);
		System.out.println("successs2");
		
	}
}
