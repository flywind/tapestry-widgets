package org.flywind.widgets.test.pages;

import java.util.List;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.flywind.widgets.WidgetSymbolConstants;
import org.flywind.widgets.core.dao.FPage;
import org.flywind.widgets.test.base.AppBase;
import org.flywind.widgets.test.business.example.ExampleService;
import org.flywind.widgets.test.entities.example.Example;

public class FTreegridTest extends AppBase {

	@Property
	private List<Example> examples;
	
	@Property
	@Persist
	private Example example;
	
	@Inject
	private ExampleService exampleService;
	

}
