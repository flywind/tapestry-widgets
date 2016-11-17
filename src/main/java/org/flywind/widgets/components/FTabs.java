package org.flywind.widgets.components;

import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.flywind.widgets.base.AbstractExtendableComponent;
import org.flywind.widgets.utils.JQueryUtils;

@SupportsInformalParameters
@Import(stylesheet={"${widget.plugins.assets.path}/easyui/themes/bootstrap/easyui.css","${widget.plugins.assets.path}/easyui/themes/icon.css"})
public class FTabs extends AbstractExtendableComponent{

	
	/**
	 * 组件参数
	 */
	@Parameter
    private JSONObject params;
	
	/**
	 * 鼠标经过触发切换
	 */
	@Parameter
	private boolean hover = false;
	
	/**
	 * 标签页样式
	 */
	@Parameter(defaultPrefix=BindingConstants.LITERAL)
	private String tabStyle;
	
	/**
	 * 标签页位置,默认为top
	 */
	@Parameter(value="top",defaultPrefix=BindingConstants.LITERAL)
	private String tabPosition;
	
    
	@Inject
	private ComponentResources componentResources;
	
    @Inject
    private JavaScriptSupport javaScriptSupport;

    void setupRender(MarkupWriter writer)
    {
        setDefaultMethod("tabs");
        writer.element("div", "id", getClientId(), "class", "easyui-tabs");
    }

    void afterRender(MarkupWriter writer)
    {
    	componentResources.renderInformalParameters(writer);
    	writer.end();
        JSONObject data = new JSONObject();
        data.put("id", getClientId());

        if (params == null)
            params = new JSONObject();

        JSONObject defaults = new JSONObject();
        defaults.put("hover", hover);
        defaults.put("tabPosition", tabPosition);
        if(StringUtils.isNotEmpty(tabStyle)){
        	defaults.put("tabStyle", tabStyle);
        }

        JQueryUtils.merge(defaults, params);

        data.put("params", defaults);

        javaScriptSupport.require("inits/init-tabs").invoke("tabs").with(data);
    }

}
