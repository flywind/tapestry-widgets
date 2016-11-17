package org.flywind.widgets.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * <p>jquery dialog 触发组件</p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月11日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class FDialogLink implements ClientElement {

	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String dialog;
	
	@Parameter(value="prop:componentResources.id",defaultPrefix=BindingConstants.LITERAL)
	private String clientId;
    
	/**
	 * 按钮样式
	 */
	@Parameter(value="btn",defaultPrefix=BindingConstants.LITERAL)
	private String cls;
	
	/**
	 * 按钮图标
	 */
	@Parameter(value="fa",defaultPrefix=BindingConstants.LITERAL)
	private String itemcls;
	
    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private AssetSource assetSource;

    @Inject
    private ComponentResources componentResources;
    
    public void beginRender(MarkupWriter writer){
    	//writer.element("a", "href", "#;", "id", getClientId());
    	writer.element("a", "id", getClientId(), "href", "#;", "class", cls);
		writer.element("i", "class", itemcls);
		writer.end();
    }
    
    public void afterRender(MarkupWriter writer){
    	componentResources.renderInformalParameters(writer);
    	writer.end();
    	
    	JSONObject params = new JSONObject();
    	params.put("triggerId", getClientId());
    	params.put("dialogId", dialog);
    	
    	javaScriptSupport.require("inits/init-dialoglink").with(params);
    }
    
    public String getClientId(){
    	return this.clientId;
    }
    
    protected String getDialog()
    {
        return this.dialog;
    }
    
}
