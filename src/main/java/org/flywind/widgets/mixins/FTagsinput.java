package org.flywind.widgets.mixins;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.flywind.widgets.utils.JQueryUtils;

@Import(stylesheet = { "${widget.plugins.path}/bootstrap-tagsinput/bootstrap-tagsinput.min.css"})
public class FTagsinput {

	@InjectContainer
	private ClientElement clientElement;
	
	@Parameter
	private JSONObject params;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	public void afterRender(){
		JSONObject d = new JSONObject();
		d.put("id", clientElement.getClientId());
		
		if(params == null){
			params = new JSONObject();
		}
		
		JSONObject defaults = new JSONObject();
		
		JQueryUtils.merge(defaults, params);
		
		d.put("params", defaults);
		json(d);
		
		javaScriptSupport.require("inits/init-tags").invoke("init").with(d);
	}
	
	protected void json(JSONObject params) {}
}
