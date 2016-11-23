package org.flywind.widgets.components;

import java.util.List;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.flywind.widgets.WidgetSymbolConstants;
import org.flywind.widgets.core.dao.FPage;
import org.flywind.widgets.core.dao.FPageHolder;
import org.flywind.widgets.utils.JQueryUtils;


/**
 * <p>easyui datagrid组件</p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月2日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Events(WidgetSymbolConstants.EASYUI_DATAGRID_DATA)
@Import(stylesheet={"${widget.plugins.assets.path}/easyui/themes/bootstrap/easyui.css","${widget.plugins.assets.path}/easyui/themes/icon.css"})
public class FDatagrid implements ClientElement{
	
	/**
	 * 默认一页显示条数
	 * 
	 * en *
	 * Page size
	 */
	public static final int PAGE_SIZE = 10;
	
	/**
	 * 客户端id,组件识别的id
	 * 
	 * en *
	 * Client id
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String clientId;
	
	/**
	 * 组件参数
	 * 
	 * en *
	 * Datagrid params
	 */
	@Parameter
	private JSONObject params;
    
    /**
     * 当设置了 pagination 属性时，初始化页面尺寸。默认10
     * 
     * en *
	 * Page size
     */
    @Parameter
    private int pageSize = PAGE_SIZE; 
    
    /**
     * 单选模式
     * 
     * en *
     * Datagrid singleSelect mode.Default:true
     */
    @Parameter
    private boolean singleSelect = true;
    
    /**
     * 语言默认是否中文
     * 
     * en *
     * Datagrid messages language,has en or zh-cn.Default:zh-CN
     */
    @Parameter
    private String language = "zh-CN";
    
    /**
     * 是否自适应布局
     * 
     * en *
     * If true,will adapt page layout.Default:false 
     */
    @Parameter
    private boolean fixLayout = false;
    
    /**
     * 空记录提示
     * 
     * en *
     * Datagrid has not data, show empty msg.
     */
    @Parameter(defaultPrefix=BindingConstants.LITERAL)
    private String emptyMsg;
    
    /**
     * 数据源
     * 
     * en *
     * Datagrid data source
     */
    @SuppressWarnings("rawtypes")
	@Parameter(required = true, autoconnect = true)
    private List source;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
    
    @Inject
    private ComponentResources componentResources;
    
    @Inject
    private Request request;
    
    @Inject
    private Messages messages;
    
    @Parameter(name = "secure", defaultPrefix = BindingConstants.LITERAL, value = "false")
    private boolean secure;
    
    public void beginRender(MarkupWriter writer){
    	writer.element("table", "class","easyui-datagrid","id", getClientId());
    }

	@Override
    public String getClientId() {
        if(InternalUtils.isBlank(clientId)) {
            clientId = InternalUtils.isNonBlank(componentResources.getInformalParameter("id", String.class)) ?
            		componentResources.getInformalParameter("id", String.class) :
                       javaScriptSupport.allocateClientId(componentResources);
        }
        return clientId.substring(0, clientId.lastIndexOf("_") == -1 ? clientId.length() : clientId.lastIndexOf("_"));
    }
	
	public void afterRender(MarkupWriter writer){
		componentResources.renderInformalParameters(writer);
		writer.end();
		
		String url = componentResources.createEventLink(WidgetSymbolConstants.EASYUI_DATAGRID_DATA).toAbsoluteURI();
		
        JSONObject data = new JSONObject();
        data.put("target", getClientId());
        
        if(params == null){
        	params = new JSONObject();
        }
        
        JSONObject defaults = new JSONObject();
        defaults.put("url", url);
        defaults.put("method", "post");
        defaults.put("width", "auto");
        defaults.put("fit", true);
        defaults.put("fitColumns", false);
        defaults.put("pagination", true);
        defaults.put("rownumbers", true);
        defaults.put("loadMsg", messages.get("loading-data"));
        defaults.put("remoteSort", false);
        defaults.put("pageSize", pageSize);
        defaults.put("emptyMsg", emptyMsg == null ? messages.get("emptyMsg") : emptyMsg);
        defaults.put("striped", true);
        defaults.put("nowrap", true);
        defaults.put("toggleColumns", true);
        defaults.put("singleSelect", singleSelect);
        defaults.put("language", language);
        defaults.put("fixLayout", fixLayout);
        
        JQueryUtils.merge(defaults, params);
        data.put("params", defaults);
        
        configure(data);
		
		javaScriptSupport.require("inits/init-datagrid").priority(InitializationPriority.LATE).invoke("init").with(data);
		
	}
    
	@OnEvent(WidgetSymbolConstants.EASYUI_DATAGRID_DATA)
	public JSONObject onData() throws Exception {
		String pageStr = request.getParameter("page");
		String pageSizeStr = request.getParameter("rows");

        int pageNumber = 1;
        int pagesize = pageSize;
        if (NumberUtils.isNumber(pageStr)) {
        	pageNumber = NumberUtils.toInt(pageStr);
        }
        if (pageNumber < 0) {
        	pageNumber = 1;
        }
        if(NumberUtils.isNumber(pageSizeStr)){
        	pagesize = NumberUtils.toInt(pageSizeStr);
        }
        if(pagesize < 10){
        	pagesize = 10;
        }
        
        FPage pageInfo = new FPage();
        pageInfo.setPageNumber(pageNumber);
        pageInfo.setPageSize(pagesize);
        FPageHolder.setPage(pageInfo);
        request.setAttribute("page", pageInfo);
  
        componentResources.triggerEvent(WidgetSymbolConstants.EASYUI_DATAGRID_LOAD_DATA, null, null);
            
        return JQueryUtils.toGridJson(source, pageInfo.getRowCount());
	}
	
	protected void configure(JSONObject params){};
}
