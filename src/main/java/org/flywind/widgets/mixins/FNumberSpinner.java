package org.flywind.widgets.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.flywind.widgets.utils.JQueryUtils;

@Import(stylesheet={"${widget.plugins.assets.path}/easyui/themes/bootstrap/easyui.css","${widget.plugins.assets.path}/easyui/themes/icon.css"})
public class FNumberSpinner {
	
	/**
	 * 是否可编辑,默认:false
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private boolean editable = true;
	
	/**
	 * 配置参数可以json
	 */
	@Parameter
	private JSONObject params;
	
	@InjectContainer
	private ClientElement clientElement;
	
	@Inject
    private JavaScriptSupport javaScriptSupport;
	
	public void afterRender(){
		JSONObject data = new JSONObject();
		data.put("id", clientElement.getClientId());
		
		if(params == null){
			params = new JSONObject();
		}
		
		JSONObject defaults = new JSONObject();
		defaults.put("editable", editable);
		
		JQueryUtils.merge(defaults, params);
		
		data.put("params", defaults);
		
		javaScriptSupport.require("inits/init-numberspinner").with(data);
	}
	
	
}
