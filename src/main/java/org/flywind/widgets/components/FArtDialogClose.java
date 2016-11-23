package org.flywind.widgets.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.flywind.widgets.base.AbstractExtendableComponent;

public class FArtDialogClose extends AbstractExtendableComponent{

	/**
	 * 关闭的弹出层ID
	 * 
	 * en *
	 * Closed dialog's id
	 */
	@Parameter(required=true, defaultPrefix=BindingConstants.LITERAL)
	private String close;

	@Inject
    private JavaScriptSupport javaScriptSupport;
	
	@Inject
    private ComponentResources componentResources;
	
	/**
	 * 按钮样式
	 * 
	 * en *
	 * Dialog button css class name
	 */
	@Parameter(value="btn",defaultPrefix=BindingConstants.LITERAL)
	private String cls;
	
	void setupRender()
    {
        setDefaultMethod("artdialogclose");
    }
	
	void beginRender(MarkupWriter writer){
		writer.element("a", "href", "#;", "class", cls);
		writer.getElement().forceAttributes("id", getClientId());
	}
	
	void afterRender(MarkupWriter writer){
		componentResources.renderInformalParameters(writer);
		writer.end();
		
		JSONObject data = new JSONObject();
		data.put("id", getClientId());
		data.put("closeId", close);
		
		javaScriptSupport.require("inits/init-fartdialogclose").priority(InitializationPriority.EARLY).invoke("init").with(data);;
		
	}
}
