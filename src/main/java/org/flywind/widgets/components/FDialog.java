package org.flywind.widgets.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.flywind.widgets.utils.JQueryUtils;

/**
 * <p>easyui dialog组件</p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月26日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Import(stylesheet={"${widget.plugins.assets.path}/easyui/themes/bootstrap/easyui.css","${widget.plugins.assets.path}/easyui/themes/icon.css"})
public class FDialog implements ClientElement{

	/**
	 * 弹出层id
	 */
	@Parameter(value="prop:componentResources.id",defaultPrefix=BindingConstants.LITERAL)
	private String clientId;
	
	/**
	 * 组件参数
	 */
	@Parameter
	private JSONObject params;
	
	@Inject
	private Messages messages;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	@Inject
	private ComponentResources componentResources;
	
	public void beginRender(MarkupWriter writer){
		writer.element("div", "id", getClientId(), "style", "display:none");
	}
	
	public void afterRender(MarkupWriter writer){
		componentResources.renderInformalParameters(writer);
		writer.end();
		
		JSONObject data = new JSONObject();
		data.put("id", getClientId());
		
		if(params == null){
			params = new JSONObject();
		}
		
		JSONObject defaults = new JSONObject();
		defaults.put("modal", true);
        defaults.put("resizable", false);
        defaults.put("title", messages.get("tip-label"));
        defaults.put("draggable", true);
        defaults.put("width", 600);
        defaults.put("height", 300);
        defaults.put("closed", true);
        defaults.put("cache", false);
        
        JQueryUtils.merge(defaults, params);
        data.put("params", defaults);
        configure(data);
        
        javaScriptSupport.require("inits/init-dialog").invoke("dialog").with(data);
	}
	
	protected void configure(JSONObject params){}
	
	public String getClientId()
    {
        return this.clientId;
    }
}
