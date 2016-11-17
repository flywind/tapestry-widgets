package org.flywind.widgets.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.flywind.widgets.utils.JQueryUtils;

/**
 * <p>确认框组件</p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月26日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Import(stylesheet={"${widget.plugins.assets.path}/easyui/themes/bootstrap/easyui.css","${widget.plugins.assets.path}/easyui/themes/icon.css"})
public class FConfirm {
	/**
	 * 显示确认信息.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String message;

	/**
	 * 弹出层标题.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String title;

	/**
	 * 确定按钮的文字.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String validationMsg;

	/**
	 * 取消按钮的文字.
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String cancelMsg;

	/**
	 * 确定框高度.
	 */
	@Parameter(value = "150", defaultPrefix = BindingConstants.LITERAL)
	private int height;

	/**
	 * 确定框宽度.
	 */
	@Parameter(value = "300", defaultPrefix = BindingConstants.LITERAL)
	private int width;
	/**
	 * 是否显示弹出层背景遮罩.
	 */
	@Parameter(value = "true", defaultPrefix = BindingConstants.LITERAL)
	private boolean isModal;

	/**
	 * 弹出层是否可以重置大小.
	 */
	@Parameter(value = "false", defaultPrefix = BindingConstants.LITERAL)
	private boolean isResizable;

	/**
	 * 弹出层是否可以拖动.
	 */
	@Parameter(value = "true", defaultPrefix = BindingConstants.LITERAL)
	private boolean isDraggable;

	/**
	 * 是否使用浏览器确定框,默认为false.
	 */
	@Parameter(value = "false", defaultPrefix = BindingConstants.LITERAL)
	private boolean useDefaultConfirm;

	
	/**
	 * 配置参数可以json
	 */
	@Parameter
	private JSONObject params;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @InjectContainer
    private ClientElement element;

    @Inject
    private ComponentResources resources;
    
    @Inject
    private Messages messages;

    public void afterRender()
    {
    	JSONObject config = new JSONObject();

    	String clientId = element.getClientId();

    	config.put("id", clientId);

    	config.put("title", title != null ? title : messages.get("title-label"));
    	config.put("message", message != null ? message : messages.get("message-label"));
    	config.put("validationMsg", validationMsg != null ? validationMsg : messages.get("validationMsg-label"));
    	config.put("cancelMsg", cancelMsg != null ? cancelMsg : messages.get("cancelMsg-label"));

    	config.put("useDefaultConfirm", useDefaultConfirm);
    	config.put("isModal", isModal);
    	config.put("isResizable", isResizable);
    	config.put("isDraggable", isDraggable);
    	config.put("height", height);
    	config.put("width", width);
    	
    	/*
    	 * 如果有json 参数 配置将合并到配置
    	 */
    	if (resources.isBound("params"))
    	{
    		JQueryUtils.merge(config, params);
    	}

    	javaScriptSupport.require("inits/init-confirm").priority(InitializationPriority.EARLY).with(config);

    }
}
