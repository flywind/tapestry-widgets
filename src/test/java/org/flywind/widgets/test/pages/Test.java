package org.flywind.widgets.test.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.flywind.widgets.test.base.AppBase;

public class Test extends AppBase {

	@Property
	private String name;
}
