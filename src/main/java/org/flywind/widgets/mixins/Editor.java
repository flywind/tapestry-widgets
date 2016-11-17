package org.flywind.widgets.mixins;

import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.upload.services.MultipartDecoder;
import org.flywind.widgets.WidgetSymbolConstants;
import org.flywind.widgets.services.AjaxUploadDecoder;
import org.flywind.widgets.utils.JQueryUtils;

/**
 * 富文本编辑组件(基于xhEditor)
 */
@Events( { WidgetSymbolConstants.AJAX_UPLOAD, WidgetSymbolConstants.NON_XHR_UPLOAD } )
@Import(stylesheet = { "${widget.plugins.path}/editor/xheditor/xheditor_skin/default/ui.css",
		"${widget.plugins.path}/editor/xheditor/xheditor_skin/default/iframe.css"})
public class Editor {

	@InjectContainer
	private ClientElement clientElement;
	
	public final static String TOOLS_FULL = "full";// 全部功能
	public final static String TOOLS_MFULL = "mfull";// 多行完全
	public final static String TOOLS_SIMPLE = "simple";// 简单
	public final static String TOOLS_MINI = "mini";// 迷你
	
	@Parameter(value = TOOLS_FULL, allowNull = true, defaultPrefix = BindingConstants.LITERAL)
	private String tools;
	
	@Parameter
	private JSONObject params;
	
	@Parameter(value = "true", defaultPrefix = BindingConstants.LITERAL)
	private boolean immediate;
	
	@Parameter(value = "true", defaultPrefix = BindingConstants.LITERAL)
	private boolean uploadImg;
	
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String upImgUrl;

	
	@Parameter(value="jpg,jpeg,gif,png",defaultPrefix=BindingConstants.LITERAL)
	private String upImgExt;
	
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String upFlashUrl;
	
	@Parameter(value="swf",defaultPrefix=BindingConstants.LITERAL)
	private String upFlashExt;
	
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String upMediaUrl;
	
	@Parameter(value="wmv,avi,wma,mp3,mid,mp4",defaultPrefix=BindingConstants.LITERAL)
	private String upMediaExt;
	
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private int height = 300;
	
	@Parameter
    private Object[] context;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	@Inject
	private ComponentResources resources;
	
	@Inject
	private MultipartDecoder multipartDecoder;
	
	@Inject
	private RequestGlobals requestGlobals;
	
	@Inject
	private ApplicationGlobals applicationGlobals;
	
	@Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @Inject
    private AjaxUploadDecoder ajaxDecoder;

    @Inject
    private Request request;

    @Inject
    private Messages messages;
	
	public void afterRender(){
		
		JSONObject data = new JSONObject();
		data.put("id", clientElement.getClientId());
		
		if(params == null){
			params = new JSONObject();
		}
		
		JSONObject defaults = new JSONObject();

		if(StringUtils.isNotBlank(upImgUrl)){
			defaults.put("upImgUrl", upImgUrl);
			defaults.put("upImgExt", upImgExt);
		}
		defaults.put("height", height);
		defaults.put("tools", tools);
		
		JQueryUtils.merge(defaults, params);
		
		data.put("params", defaults);
		
		javaScriptSupport.require("inits/init-editor").invoke("init").with(data);

	}
	
	
}