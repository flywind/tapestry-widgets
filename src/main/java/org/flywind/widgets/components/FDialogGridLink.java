package org.flywind.widgets.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * <p>jquery dialog datagrid 触发组件</p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月11日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class FDialogGridLink implements ClientElement {

	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String dialog;
	
	@Parameter(value="prop:componentResources.id",defaultPrefix=BindingConstants.LITERAL)
	private String clientId;
	
	/**
	 * 组件的文本
	 */
	@Parameter(required=true,defaultPrefix=BindingConstants.LITERAL)
	private String text;
    
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
	
	/**
	 * datagrid id
	 */
	@Parameter(required=true,defaultPrefix=BindingConstants.LITERAL)
	private String gridId;
	
	/**
	 * 无选择datagrid时,警告框的内容
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String alertMsg;
	
	/**
	 * 无选择datagrid时,警告框的标题
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String alertTitle;
	
	/**
	 * ajax zone
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String zone;
	
	/**
	 * ajax url
	 */
	@Parameter(required=true,defaultPrefix=BindingConstants.LITERAL)
	private String url;
	
    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private AssetSource assetSource;

    @Inject
    private ComponentResources componentResources;
    
    @Inject
	private Messages messages;
    
    public void beginRender(MarkupWriter writer){
    	//writer.element("a", "href", "#;", "id", getClientId());
    	writer.element("a", "id", getClientId(), "href", "#;", "class", cls);
		writer.element("i", "class", itemcls);
		writer.end();
		writer.write(text);
    }
    
    public void afterRender(MarkupWriter writer){
    	componentResources.renderInformalParameters(writer);
    	writer.end();
    	
    	JSONObject params = new JSONObject();
    	params.put("triggerId", getClientId());
    	params.put("dialogId", dialog);
    	params.put("gridId", gridId);
    	params.put("zoneId", zone);
    	params.put("url", url);
    	params.put("alertTitle", alertTitle !=null ? alertTitle : messages.get("alertTitle"));
    	params.put("alertMsg", alertMsg !=null ? alertMsg : messages.get("alertMsg"));
    	
    	javaScriptSupport.require("inits/init-dialoggridlink").with(params);
    }
    
    public String getClientId(){
    	return this.clientId;
    }
    
    protected String getDialog()
    {
        return this.dialog;
    }
    
}
