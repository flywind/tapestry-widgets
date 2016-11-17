package org.flywind.widgets.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.flywind.widgets.base.AbstractExtendableComponent;
import org.flywind.widgets.utils.JQueryUtils;

public class FArtDialogLink extends AbstractExtendableComponent
	{
	    /**
	     * 被打开的弹出层.
	     */
		@Property
	    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	    private String dialog;
		
		/**
		 * 组件参数
		 */
		@Parameter
		private JSONObject params;
		
		@Inject
		private Messages messages;
		
		/**
		 * 按钮样式
		 */
		@Parameter(value="btn",defaultPrefix=BindingConstants.LITERAL)
		private String cls;
		
	    @Inject
	    private JavaScriptSupport javaScriptSupport;

	    @Inject
	    private ComponentResources componentResources;

	    void setupRender()
	    {
	        setDefaultMethod("fartdialoglink");
	    }

	    void beginRender(MarkupWriter writer)
	    {
	        writer.element("a", "href", "#;", "class", cls);
	        writer.getElement().forceAttributes("id", getClientId());
	    }
	    
	    void afterRender(MarkupWriter writer)
	    {
	    	componentResources.renderInformalParameters(writer);
	        writer.end();
	        
	        JSONObject data = new JSONObject();
			data.put("id", getClientId());
			data.put("dialog", dialog);
			
			if(params == null){
				params = new JSONObject();
			}
			
			JSONObject defaults = new JSONObject();
			defaults.put("fixed", true);
	        defaults.put("resize", false);
	        defaults.put("title", messages.get("tip-label"));
	        defaults.put("lock", true);
	        
	        JQueryUtils.merge(defaults, params);
	        data.put("params", defaults);
	        configure(data);
	        
	        javaScriptSupport.require("inits/init-fartdialoglink").priority(InitializationPriority.EARLY).invoke("init").with(data);
	    }
	    
	    protected void configure(JSONObject params){};
}