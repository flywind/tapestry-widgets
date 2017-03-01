package org.flywind.widgets.components;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.flywind.widgets.WidgetSymbolConstants;
import org.flywind.widgets.core.dao.FPage;
import org.flywind.widgets.core.dao.FPageHolder;
import org.flywind.widgets.utils.JQueryUtils;


/**
 * <p>分页组件</p>
 * 
 * @author flywind(飞风)
 * @date 2015年12月5日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Import(stylesheet={"${widget.plugins.assets.path}/paginate/bootstrap-paginate.css"})
@Events({WidgetSymbolConstants.PAGER_DATA,WidgetSymbolConstants.PAGER_LOAD_DATA})
public class FPaginate implements ClientElement {
	
	/**
	 * 客户端id,组件识别的id
	 * 
	 * en *
	 * Client id
	 */
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String clientId;
	
	/**
	 * ajax作用区域
	 * 
	 * en *
	 * Updated zone's id
	 */
	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String zone;
    
    /**
     * 查询参数
     * 
     * en *
     * Form query params,examples:username,age
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String queryParams;

	/**
	 * 组件参数
	 * 
	 * en *
	 * Paginate params
	 */
	@Parameter
    private JSONObject params;
	
	/**
	 * 当前页显示的记录数
	 * 
	 * en *
	 * Page size
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private int pageSize = 5;
	
	/**
	 * 总页数
	 * 
	 * en *
	 * Total pages
	 */
	@Property
	private int totalPages;
	
	/**
	 * 分页大小,默认:normal,类型有:large,small,mini
	 * 
	 * Size,has large,small,mini,normal.Default:normal
	 */
	@Parameter(value="normal",defaultPrefix=BindingConstants.LITERAL)
	private String size;
	
	/**
     * 数据源
     * 
     * en *
     * Data source
     */
    @SuppressWarnings("rawtypes")
	@Parameter(required = true, autoconnect = true)
    private List source;
	
	@Inject
	private ComponentResources componentResources;
	
    @Inject
    private JavaScriptSupport javaScriptSupport;
    
    @Inject
    private Request request;
    
    @Parameter(name = "secure", defaultPrefix = BindingConstants.LITERAL, value = "false")
    private boolean secure;
    
	@Override
    public String getClientId() {
        if(InternalUtils.isBlank(clientId)) {
            clientId = InternalUtils.isNonBlank(componentResources.getInformalParameter("id", String.class)) ?
            		componentResources.getInformalParameter("id", String.class) :
                       javaScriptSupport.allocateClientId(componentResources);
        }
        return clientId.substring(0, clientId.lastIndexOf("_") == -1 ? clientId.length() : clientId.lastIndexOf("_"));
    }

    public void setupRender(MarkupWriter writer)
    {
        writer.element("div", "id", getClientId());
    }
    
    public void afterRender(MarkupWriter writer){
    	componentResources.renderInformalParameters(writer);
        writer.end();

        String url = componentResources.createEventLink(WidgetSymbolConstants.PAGER_DATA).toAbsoluteURI(secure);
        componentResources.triggerEvent(WidgetSymbolConstants.PAGER_DATA, null, null);
        
    	JSONObject data = new JSONObject();
    	data.put("id", getClientId());
    	
    	if(params == null){
    		params = new JSONObject();
    	}
    	
    	JSONObject defaults = new JSONObject();
    	defaults.put("url", url);
    	defaults.put("zoneId", zone);
    	defaults.put("pageSize", pageSize);
    	defaults.put("totalPages", totalPages);
    	defaults.put("size", size);
    	defaults.put("queryParams", queryParams);
    	
    	JQueryUtils.merge(defaults, params);
    	
    	data.put("params", defaults);
    	
    	config(data);
    	
    	javaScriptSupport.require("inits/init-paginate").invoke("fpaginate").with(data);
    	
	
    }
    
    @OnEvent(WidgetSymbolConstants.PAGER_DATA)
    public void onData(){

    	String pageStr = request.getParameter("pageNumber");
    	if(StringUtils.isNotBlank(queryParams)){
    		String[] p = queryParams.split(",");
        	for(String s : p){
        		request.setAttribute(s, request.getParameter(s));
        	}
    	}
    	
    	int pageNumber = 1;
        int pagesize = pageSize;
        if (NumberUtils.isNumber(pageStr)) {
        	pageNumber = NumberUtils.toInt(pageStr);
        }
        if (pageNumber < 0) {
        	pageNumber = 1;
        }
        if(pagesize < 0){
        	pagesize = 5;
        }
        
        FPage pageInfo = new FPage();
        pageInfo.setPageNumber(pageNumber);
        pageInfo.setPageSize(pagesize);
        FPageHolder.setPage(pageInfo);
        request.setAttribute("page", pageInfo);
  
        componentResources.triggerEvent(WidgetSymbolConstants.PAGER_LOAD_DATA, null, null);
        
        totalPages = pageInfo.getPageCount();

    }
 
    protected void config(JSONObject params){}
    
    
    
}
