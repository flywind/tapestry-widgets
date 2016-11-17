package org.flywind.widgets.test.pages;

import java.util.List;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.corelib.components.Zone;
import org.flywind.widgets.test.base.AppBase;

public class FDatagridTest4 extends AppBase {
	
	@Property
	@Persist
	private String id;
	
	@InjectComponent
	private Zone meZone;
	
	void onActivate(@RequestParameter(value="id") String id){
		System.out.println(id);
		this.id = id;
	}
	
	public void onClear(EventContext ec){
		if(ec.getCount() == 0){
			id = null;
		}
		ajaxResponseRenderer.addRender(meZone);
	}

}
