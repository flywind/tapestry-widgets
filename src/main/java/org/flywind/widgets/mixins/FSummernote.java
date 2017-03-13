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
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.flywind.widgets.utils.JQueryUtils;

@Import(stylesheet={"${widget.plugins.path}/summernote/summernote.min.css"})
public class FSummernote  {
	
	public final static String TOOLS_FULL = "full";// All tools
	public final static String TOOLS_DEFAULT = "normal";// Default tools
	
	/**
	 * 富文本编辑器工具类型
	 * 
	 * en *
	 * FSummernote tools type, has 'full','normal'.Default:normal
	 */
	@Parameter(value = TOOLS_DEFAULT, defaultPrefix = BindingConstants.LITERAL)
	private String tools;
	
	/**
	 * 组件参数
	 * 
	 * en *
	 * Summernote's params
	 */
	@Parameter
	private JSONObject params;
	
	/**
     * 语言默认是中文
     * 
     * en *
     * Summernote messages language,has en or zh-cn.Default:zh-CN
     */
	@Parameter(value="zh-CN",defaultPrefix=BindingConstants.LITERAL)
    private String language;
	
	/**
	 * 上传到服务器
	 * 
	 * en *
	 * Upload to server,default:false
	 */
	@Parameter(value="false")
	private boolean uploadToServer;
	
	/**
	 * 默认高度300(px)
	 * 
	 * en *
	 * Default height 300 (px)
	 */
	@Parameter(value="300",defaultPrefix=BindingConstants.LITERAL)
	private String height;
	
	@Parameter
	private String modalImageTitle;
	
	@Parameter
	private String modalImageContent;
	
	@Parameter
	private String modalImageText;
	
	/**
	 * 上传图片的URL请求
	 * 
	 * en *
	 * If uploadToServer is true, upload img url request.
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String url;
	
	@Inject
	private ComponentResources componentResources;
	
	@InjectContainer
	private ClientElement clientElement;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	@Inject
    private Messages messages;
	
	public void setupRender(MarkupWriter writer){
		writer.element("input", "id","summernoteLangId","type","hidden", "value",language.toLowerCase());
    	writer.end();
	}
	
	public void afterRender(){
		JSONObject data = new JSONObject();
		data.put("id", clientElement.getClientId());
		data.put("uploadToServer", uploadToServer);
		data.put("url", url);
		data.put("modalImageTitle", modalImageTitle == null ? messages.get("modalImageTitle") : modalImageTitle);
		data.put("modalImageContent", modalImageContent == null ? messages.get("modalImageContent") : modalImageContent);
		data.put("modalImageText", modalImageText == null ? messages.get("modalImageText") : modalImageText);
		
		if(params == null){
			params = new JSONObject();
		}
		
		JSONObject defaults = new JSONObject();
		defaults.put("height", height);
		defaults.put("tools", tools != null ? tools.toLowerCase() : tools);
		
		
		JQueryUtils.merge(defaults, params);
		
		configure(defaults);
		
		data.put("params", defaults);
		
		javaScriptSupport.require("inits/init-summernote").invoke("init").with(data);
	}
	
	protected void configure(JSONObject params){};
}
