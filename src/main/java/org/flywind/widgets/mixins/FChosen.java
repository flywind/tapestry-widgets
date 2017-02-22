package org.flywind.widgets.mixins;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.flywind.widgets.utils.JQueryUtils;

@Import(stylesheet = { "${widget.plugins.path}/chosen/chosen.min.css"})
public class FChosen {
	
	@Parameter
	private JSONObject params;
	
	@Parameter(required = true, allowNull = false)
	private JSONArray model;
	
	@InjectContainer
	private ClientElement clientElement;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	public void afterRender(){
		JSONObject d = new JSONObject();
		d.put("id", clientElement.getClientId());
		d.put("model", model);
		
		if(params == null){
			params = new JSONObject();
		}
		
		JSONObject defaults = new JSONObject();
		defaults.put("width", "100%");
		defaults.put("max_selected_options", 4);
		
		JQueryUtils.merge(defaults, params);
		
		d.put("params", defaults);
		
		json(d);
		
		javaScriptSupport.require("inits/init-chosen").invoke("init").with(d);
	}
	
	protected void json (JSONObject json){}
	
}
