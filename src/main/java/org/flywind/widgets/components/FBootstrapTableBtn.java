package org.flywind.widgets.components;

import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(stylesheet={"${widget.plugins.assets.path}/artdialog/skin/default.css"})
public class FBootstrapTableBtn implements ClientElement {

	/**
	 * 客户端id
	 * 
	 * en *
	 * Client id
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String clientId;
	
	/**
	 * 组件的文本
	 * 
	 * en *
	 * Bootstrap table button label
	 */
	@Parameter(required=true,defaultPrefix=BindingConstants.LITERAL)
	private String text;
	
	/**
	 * 组件类型:page(pagelink),event(eventlink)
	 * 
	 * en *
	 * Bootstrap table button type,has page(pagelink) or event(eventlink).Default:event
	 */
	@Parameter(value="event",defaultPrefix=BindingConstants.LITERAL)
	private String linkType;
	
	/**
	 * 按钮样式
	 * 
	 * en *
	 * Bootstrap table button css class name.Default:btn
	 */
	@Parameter(value="btn",defaultPrefix=BindingConstants.LITERAL)
	private String cls;
	
	/**
	 * 按钮图标
	 * 
	 * en *
	 * Bootstrap table button icon css class name.Default:fa
	 */
	@Parameter(value="fa",defaultPrefix=BindingConstants.LITERAL)
	private String itemcls;
	
	/**
	 * ajax zone
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String zone;
	
	/**
	 * 客户端事件类型
	 * 
	 * en *
	 * Bootstrap table button client event.Default:click(js onClick event)
	 */
	@Parameter(value="click",defaultPrefix=BindingConstants.LITERAL)
	private String clientEvent;
	
	/**
	 * datagrid id
	 * 
	 * en *
	 * Edited datagrid's id
	 */
	@Parameter(required=true,defaultPrefix=BindingConstants.LITERAL)
	private String gridId;
	
	/**
	 * ajax url
	 * 
	 * en *
	 * Handling requests for URL
	 */
	@Parameter(required=true,defaultPrefix=BindingConstants.LITERAL)
	private String url;
	
	/**
	 * 没有选择数据时,警告框的内容
	 * 
	 * en *
	 * Bootstrap table has not data, the contents of the warning box
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String alertMsg;
	
	/**
	 * 编辑模式下出现多条记录时,警告框的内容
	 * 
	 * en *
	 * When a number of records appear in edit mode, the contents of the warning box
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String editMsg;
	
	/**
	 * 没有选择数据时,警告框的标题
	 * 
	 * en *
	 * Bootstrap table has not data, the contents of the warning title
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String alertTitle;
	
	/**
	 * 确认框的标题
	 * 
	 * en *
	 * Confirm title msg
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String confirmTitle;
	
	/**
	 * 确认框的内容
	 * 
	 * en *
	 * Confirm content msg
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String confirmMsg;
	
	/**
	 * 确认框按钮的文字
	 * 
	 * en *
	 * Confirm submit btn msg
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String submitMsg;
	
	/**
	 * 确认框取消按钮的文字
	 * 
	 * en *
	 * Confirm cancel btn msg
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String cancelMsg;
	
	/**
	 * 关闭弹窗口
	 * 
	 * en *
	 * Closed datagrid's id
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String closed;
	
	@Inject
	private Messages messages;
	
	@Inject
	private ComponentResources componentResources;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	@Override
    public String getClientId() {
        if(InternalUtils.isBlank(clientId)) {
            clientId = InternalUtils.isNonBlank(componentResources.getInformalParameter("id", String.class)) ?
            		componentResources.getInformalParameter("id", String.class) :
                       javaScriptSupport.allocateClientId(componentResources);
        }
        return clientId.substring(0, clientId.lastIndexOf("_") == -1 ? clientId.length() : clientId.lastIndexOf("_"));
    }
	
	public void beginRender(MarkupWriter writer){
		writer.element("a", "id", getClientId(), "class", cls);
		writer.element("i", "class", itemcls);
		writer.end();
		writer.write(text);
	}
	
	public void afterRender(MarkupWriter writer){
		componentResources.renderInformalParameters(writer);
		writer.end();
		JSONObject opts = new JSONObject();
		opts.put("id", getClientId());
		opts.put("linkType", linkType);
		opts.put("gridId", gridId);
		opts.put("zoneId", zone);
		opts.put("clientEvent", clientEvent);
		opts.put("url", url);
		opts.put("title", alertTitle !=null ? alertTitle : messages.get("alertTitle"));
		opts.put("confirmTitle", confirmTitle !=null ? confirmTitle : messages.get("confirmTitle"));
		opts.put("confirmMsg", confirmMsg !=null ? confirmMsg : messages.get("confirmMsg"));
		opts.put("submitMsg", submitMsg !=null ? submitMsg : messages.get("submitMsg"));
		opts.put("cancelMsg", cancelMsg !=null ? cancelMsg : messages.get("cancelMsg"));
		opts.put("editMsg", editMsg !=null ? editMsg : messages.get("editMsg"));
		opts.put("closed", closed);
		if(StringUtils.isEmpty(alertMsg)){
			opts.put("alertMsg", messages.get("alertMsg"));
		}else{
			opts.put("alertMsg", alertMsg);
		}
		
		javaScriptSupport.require("inits/init-bootstraptablebtn").with(opts);
	}
}
