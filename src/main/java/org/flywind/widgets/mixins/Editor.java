package org.flywind.widgets.mixins;

import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
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
	
	/**
	 * 富文本编辑器工具类型
	 * 
	 * en *
	 * xhEditor tools type, has 'full','mfull','simple','mini'.Default:full
	 */
	@Parameter(value = TOOLS_FULL, defaultPrefix = BindingConstants.LITERAL)
	private String tools;
	
	/**
	 * 编辑器参数配置
	 * 
	 * en *
	 * xhEditor params
	 */
	@Parameter
	private JSONObject params;
	
	/**
	 * 图片上传请求连接
	 * 
	 * en *
	 * Upload image ajax request url
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String upImgUrl;
	
	/**
	 * 容许上传的图片格式
	 * 
	 * en *
	 * Allowed upload image file format
	 */
	@Parameter(value="jpg,jpeg,gif,png",defaultPrefix=BindingConstants.LITERAL)
	private String upImgExt;
	
	/**
	 * flash上传请求连接
	 * 
	 * en *
	 * Upload image flash request url
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String upFlashUrl;
	
	/**
	 * 容许上传的flash格式
	 * 
	 * en *
	 * Allowed upload flash file format
	 */
	@Parameter(value="swf",defaultPrefix=BindingConstants.LITERAL)
	private String upFlashExt;
	
	/**
	 * 视频上传请求连接
	 * 
	 * en *
	 * Upload image media request url
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String upMediaUrl;
	
	/**
	 * 容许上传的视频格式
	 * 
	 * en *
	 * Allowed upload media file format
	 */
	@Parameter(value="wmv,avi,wma,mp3,mid,mp4",defaultPrefix=BindingConstants.LITERAL)
	private String upMediaExt;
	
	/**
     * 语言默认是否中文
     * 
     * en *
     * Datagrid messages language,has en or zh-cn.Default:zh-CN
     */
	@Parameter(value="zh-CN",defaultPrefix=BindingConstants.LITERAL)
    private String language;
	
	/**
	 * 编辑器的初始化高度
	 * 
	 * en *
	 * xhEditor height.Default:300
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private int height = 300;
	
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
    
    public void beginRender(MarkupWriter writer){
    	
    }
	
	public void afterRender(MarkupWriter writer){
		writer.element("input", "id","xhEditorLangId","type","hidden", "value",language.toLowerCase());
    	writer.end();
		resources.renderInformalParameters(writer);
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
		if(StringUtils.isNotBlank(upFlashUrl)){
			defaults.put("upFlashUrl", upFlashUrl);
			defaults.put("upFlashExt", upFlashExt);
		}
		if(StringUtils.isNotBlank(upMediaUrl)){
			defaults.put("upMediaUrl", upMediaUrl);
			defaults.put("upMediaExt", upMediaExt);
		}
		defaults.put("height", height);
		defaults.put("tools", tools);
		
		JQueryUtils.merge(defaults, params);
		
		data.put("params", defaults);
		
		javaScriptSupport.require("inits/init-editor").invoke("init").with(data);

	}
	
	
}