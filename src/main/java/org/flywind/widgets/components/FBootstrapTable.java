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
import org.apache.tapestry5.annotations.Property;
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

@Events(WidgetSymbolConstants.BOOTSTRAP_TABLE_DATA)
@Import(stylesheet={"${widget.plugins.path}/bootstrap-table/bootstrap-table.min.css",
		"${widget.plugins.path}/x-editable/css/bootstrap-editable.css"})
public class FBootstrapTable implements ClientElement{
	
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
     * 当设置了 pagination 属性时，初始化页面尺寸。默认10
     * 
     * en *
	 * Page size
     */
    @Parameter
    private int pageSize = PAGE_SIZE; 
	
	/**
	 * 组件参数
	 * 
	 * en *
	 * Bootstrap table params
	 */
	@Parameter
	private JSONObject params;
	
	/**
     * 语言默认是否中文
     * 
     * en *
     * Datagrid messages language,has en or zh-cn.Default:zh-CN
     */
	@Parameter(value="zh-CN",defaultPrefix=BindingConstants.LITERAL)
    private String language;
	
	@SuppressWarnings("rawtypes")
	@Parameter(required = true, autoconnect = true)
    private List source;
	
	@Parameter
	private JSONArray columns;

	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	@Inject
	private Request request;
	
	@Inject
	private ComponentResources componentResources;
	
	@Property
	private String url;
	
	@Override
    public String getClientId() {
        if(InternalUtils.isBlank(clientId)) {
            clientId = InternalUtils.isNonBlank(componentResources.getInformalParameter("id", String.class)) ?
            		componentResources.getInformalParameter("id", String.class) :
                       javaScriptSupport.allocateClientId(componentResources);
        }
        return clientId.substring(0, clientId.lastIndexOf("_") == -1 ? clientId.length() : clientId.lastIndexOf("_"));
    }
	
	public void setupRender(MarkupWriter writer){
		writer.element("table", "id", getClientId(), "class", "table table-bordered table-striped");
		writer.end();
		writer.element("input", "id","tableLangId","type","hidden", "value",language.toLowerCase());
    	writer.end();
	}
	
	public void afterRender(){
		url = componentResources.createEventLink(WidgetSymbolConstants.BOOTSTRAP_TABLE_DATA).toAbsoluteURI();
		
		JSONObject data = new JSONObject();
		data.put("id", getClientId());
		data.put("columns", columns);
		
		if(params == null){
			params = new JSONObject();
		}
		
		JSONObject defaults = new JSONObject();
		defaults.put("url", url);
		defaults.put("pagination", true);
		defaults.put("pageNumber", 1);
		defaults.put("sidePagination", "server");
		defaults.put("pageSize", pageSize);
		
		JQueryUtils.merge(defaults, params);
		
		data.put("params", defaults);
		configure(data);
		
		javaScriptSupport.require("inits/init-bootstraptable").invoke("init").with(data);
	}
	
	protected void configure(JSONObject params){};
	
	@OnEvent(WidgetSymbolConstants.BOOTSTRAP_TABLE_DATA)
	public JSONObject onData() throws Exception{
		try {
			String offset = request.getParameter("offset");
			String limit = request.getParameter("limit");
			String order = request.getParameter("order");
			
			int pageNumber = NumberUtils.toInt(offset)/NumberUtils.toInt(limit) + 1;

	        int pagesize = pageSize;
	
	        if (pageNumber < 0) {
	        	pageNumber = 1;
	        }
	        if(NumberUtils.isNumber(limit)){
	        	pagesize = NumberUtils.toInt(limit);
	        }
	        if(pagesize < 10){
	        	pagesize = 10;
	        }
	        
	        FPage pageInfo = new FPage();
	        pageInfo.setPageNumber(pageNumber);
	        pageInfo.setPageSize(pagesize);
	        pageInfo.setSortOrder(order);
	        FPageHolder.setPage(pageInfo);
	        request.setAttribute("page", pageInfo);
	  
	        componentResources.triggerEvent(WidgetSymbolConstants.BOOTSTRAP_TABLE_LOAD_DATA, null, null);
	            
	        return JQueryUtils.toGridJson(source, pageInfo.getRowCount());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
		
	}
}
