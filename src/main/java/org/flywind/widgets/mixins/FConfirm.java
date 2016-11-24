package org.flywind.widgets.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
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
@Import(stylesheet={"${widget.plugins.assets.path}/artdialog/skin/default.css"})
public class FConfirm {
	/**
	 * 显示确认信息.
	 * 
	 * en *
	 * Display confirmation message
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String message;

	/**
	 * 弹出层标题.
	 * 
	 * en *
	 * FConfirm title
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String title;

	/**
	 * 确定按钮的文字.
	 * 
	 * en *
	 * FConfirm submit btn's label
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String validationMsg;

	/**
	 * 取消按钮的文字.
	 * 
	 * en *
	 * FConfirm cancel btn's label
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String cancelMsg;

	/**
	 * 容器填充.
	 * 
	 * en *
	 * FConfirm padding
	 */
	@Parameter(value = "0", defaultPrefix = BindingConstants.LITERAL)
	private String padding;

	/**
	 * 是否显示弹出层背景遮罩.
	 * 
	 * en *
	 * If true display a popup background mask.
	 */
	@Parameter(value = "true", defaultPrefix = BindingConstants.LITERAL)
	private boolean lock;

	/**
	 * 是否使用浏览器确定框,默认为false.
	 * 
	 * en *
	 * If true use the browser to determine the box, the default is false.
	 */
	@Parameter(value = "false", defaultPrefix = BindingConstants.LITERAL)
	private boolean useDefaultConfirm;

	
	/**
	 * 配置参数
	 * 
	 * en *
	 * FConfirm params
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
    	config.put("lock", lock);
    	config.put("padding", padding);
    	
    	if (resources.isBound("params"))
    	{
    		JQueryUtils.merge(config, params);
    	}

    	javaScriptSupport.require("inits/init-confirm").priority(InitializationPriority.EARLY).with(config);

    }
}
