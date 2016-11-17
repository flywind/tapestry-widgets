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
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(stylesheet={"${widget.plugins.assets.path}/easyui/themes/bootstrap/easyui.css","${widget.plugins.assets.path}/easyui/themes/icon.css"})
public class FDatagridBtn implements ClientElement {

	/**
	 * 客户端id
	 */
	@Parameter(value="prop:componentResources.id",defaultPrefix=BindingConstants.LITERAL)
	private String clientId;
	
	/**
	 * 组件的文本
	 */
	@Parameter(required=true,defaultPrefix=BindingConstants.LITERAL)
	private String text;
	
	/**
	 * 组件类型:link(pagelink),event(eventlink)
	 */
	@Parameter(value="event",defaultPrefix=BindingConstants.LITERAL)
	private String linkType;
	
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
	 * ajax zone
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String zone;
	
	/**
	 * 客户端事件类型
	 */
	@Parameter(value="click",defaultPrefix=BindingConstants.LITERAL)
	private String clientEvent;
	
	/**
	 * datagrid id
	 */
	@Parameter(required=true,defaultPrefix=BindingConstants.LITERAL)
	private String gridId;
	
	/**
	 * ajax url
	 */
	@Parameter(required=true,defaultPrefix=BindingConstants.LITERAL)
	private String url;
	
	/**
	 * 没有选择数据时,警告框的内容
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String alertMsg;
	
	/**
	 * 编辑模式下出现多条记录时,警告框的内容
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String editMsg;
	
	/**
	 * 没有选择数据时,警告框的标题
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String alertTitle;
	
	/**
	 * 确认框的标题
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String confirmTitle;
	
	/**
	 * 确认框的内容
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String confirmMsg;
	
	/**
	 * 确认框按钮的文字
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String submitMsg;
	
	/**
	 * 确认框取消按钮的文字
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String cancelMsg;
	
	/**
	 * 关闭弹窗口
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String closed;
	
	/**
	 * 编辑模式:CREATE, REVIEW, UPDATE;
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String editorMode;
	
	@Inject
	private Messages messages;
	
	@Inject
	private ComponentResources componentResources;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
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
		opts.put("closed", closed);
		if(StringUtils.isEmpty(alertMsg)){
			opts.put("alertMsg", messages.get("alertMsg"));
		}else{
			opts.put("alertMsg", alertMsg);
		}
		if(StringUtils.isNotEmpty(editorMode)){
			opts.put("editorMode", editorMode);
		}
		
		javaScriptSupport.require("inits/init-fDatagridBtn").with(opts);
	}
	
	public String getClientId(){
    	return this.clientId;
    }
}
