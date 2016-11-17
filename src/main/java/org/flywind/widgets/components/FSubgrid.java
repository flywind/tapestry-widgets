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
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.flywind.widgets.WidgetSymbolConstants;
import org.flywind.widgets.core.dao.FPage;
import org.flywind.widgets.core.dao.FPageHolder;
import org.flywind.widgets.utils.JQueryUtils;

/**
 * <p>内嵌grid组件</p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月26日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Events({WidgetSymbolConstants.EASYUI_SUBGRID_DATA})
@Import(stylesheet={"${widget.plugins.assets.path}/easyui/themes/bootstrap/easyui.css","${widget.plugins.assets.path}/easyui/themes/icon.css"})
public class FSubgrid implements ClientElement{
	
	/**
	 * 默认一页显示条数
	 */
	public static final int PAGE_SIZE = 10;
	
	/**
	 * 客户端id,组件识别的id
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String clientId;
	
	/**
	 * 当前页记录数
	 */
	@Parameter
    private int pageSize = PAGE_SIZE; 
    
    /**
     * 单选模式
     */
    @Parameter
    private boolean singleSelect = true;
    
    /**
     * 数据源
     */
    @SuppressWarnings("rawtypes")
	@Parameter(required = true, autoconnect = true)
    private List source;
    
    
    /**
     * 数据源记录总数
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private int total;
    
    /**
     * 子级数据请求的url
     */
    @Parameter(defaultPrefix=BindingConstants.LITERAL)
    private String subUrl;
    
    /**
     * loading提示
     */
    @Parameter(defaultPrefix=BindingConstants.LITERAL)
    private String loadMsg;
    
    /**
     * 子级的loading提示
     */
    @Parameter(defaultPrefix=BindingConstants.LITERAL)
    private String subLoadMsg;
    
    /**
     * 空记录提示
     */
    @Parameter(defaultPrefix=BindingConstants.LITERAL)
    private String emptyMsg;
    
    /**
     * 子级的空记录提示
     */
    @Parameter(defaultPrefix=BindingConstants.LITERAL)
    private String subEmptyMsg;
	
	/**
	 * 组件参数
	 */
	@Parameter
	private JSONObject params;
	
	/**
	 * 子级栏位
	 */
	@Parameter
	private JSONArray subColumns; 
	
	/**
     * 语言默认是否中文
     */
    @Parameter
    private String language = "zh-CN";
	
	/**
     * 是否自适应布局
     */
    @Parameter
    private boolean fixLayout = false;
    
    /**
     * 减去左边布局宽度
     */
    @Parameter
    private int cutWidth = 260;
    
    /**
     * 左边菜单的宽度
     */
    @Parameter
    private int menuWidth = 180;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
    
    @Inject
    private ComponentResources componentResources;
    
    @Inject
    private Request request;
    
    @Inject
    private Messages messages;
    
    public void beginRender(MarkupWriter writer){
    	writer.element("table", "class","easyui-datagrid","id", getClientId());
    }

    public void afterRender(MarkupWriter writer)
    {
    	componentResources.renderInformalParameters(writer);
		writer.end();
		
    	String url = componentResources.createEventLink(WidgetSymbolConstants.EASYUI_SUBGRID_DATA).toAbsoluteURI();
    	
    	JSONObject data = new JSONObject();
        data.put("id", getClientId());
        data.put("subColumns", subColumns);
        
        if(params == null){
        	params = new JSONObject();
        }
        
        JSONObject defaults = new JSONObject();
        defaults.put("url", url);
        defaults.put("method", "post");
        defaults.put("width", "auto");
        defaults.put("fit", true);
        defaults.put("fitColumns", true);
        defaults.put("pagination", true);
        defaults.put("rownumbers", true);
        defaults.put("loadMsg", loadMsg == null ? messages.get("loading-data") : loadMsg);
        defaults.put("subLoadMsg", subLoadMsg == null ? messages.get("loading-data") : subLoadMsg);
        defaults.put("emptyMsg", emptyMsg == null ? messages.get("emptyMsg") : emptyMsg);
        defaults.put("subEmptyMsg", subEmptyMsg == null ? messages.get("emptyMsg") : subEmptyMsg);
        defaults.put("remoteSort", false);
        defaults.put("pageSize", pageSize);
        defaults.put("striped", true);
        defaults.put("nowrap", true);
        defaults.put("singleSelect", singleSelect);
        defaults.put("subUrl", subUrl);
        defaults.put("language", language);
        defaults.put("fixLayout", fixLayout);
        defaults.put("cutWidth", cutWidth);
        defaults.put("menuWidth", menuWidth);

        
        JQueryUtils.merge(defaults, params);
        data.put("params", defaults);

        configure(data);
        
    	javaScriptSupport.require("inits/init-subgrid").invoke("init").with(data);

    }
    
    @OnEvent(WidgetSymbolConstants.EASYUI_SUBGRID_DATA)
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
  
        componentResources.triggerEvent(WidgetSymbolConstants.EASYUI_SUBGRID_LOAD_DATA, null, null);

        return JQueryUtils.toGridJson(source, pageInfo.getRowCount());
	}
    
    
    /**
	 *重写客户端id
	 */
	@Override
    public String getClientId() {
        if(InternalUtils.isBlank(clientId)) {
            clientId = InternalUtils.isNonBlank(componentResources.getInformalParameter("id", String.class)) ?
            		componentResources.getInformalParameter("id", String.class) :
                       javaScriptSupport.allocateClientId(componentResources);
        }
        return clientId.substring(0, clientId.lastIndexOf("_") == -1 ? clientId.length() : clientId.lastIndexOf("_"));
    }
    
    protected void configure(JSONObject params){};
}
