package org.flywind.widgets.test.pages;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.flywind.widgets.test.base.AppBase;

@Import(stylesheet="context:assets/styles/demo.css")
public class FConfirmTest extends AppBase {

	@InjectComponent
	private Zone meZone;
	
	@Property
	@Persist
	private int count;
	
	void setupRender(){
		count = 0;
	}
	
	public void onActionFromDel(int count){
		System.out.println(count);
		this.count += count;
		ajaxResponseRenderer.addRender(meZone);
	}
}
