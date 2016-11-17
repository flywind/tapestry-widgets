package org.flywind.widgets.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * <p>easyui dialog AJAX触发组件</p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月11日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class FDialogAjaxLink extends FDialogLink {

	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String zone;
	
	@Parameter
    private Object[] context;
	
    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private AssetSource assetSource;

    @Inject
    private ComponentResources componentResources;
    
    @Override
    public void afterRender(MarkupWriter writer){
    	componentResources.renderInformalParameters(writer);
    	writer.end();
    	
    	String link = componentResources.createEventLink(EventConstants.ACTION, context).toURI();
    	
    	JSONObject params = new JSONObject();
    	params.put("element", getClientId());
    	params.put("zoneId", zone);
    	params.put("dialogId", getDialog());
    	params.put("url", link);
    	
    	javaScriptSupport.require("inits/init-dialogajaxlink").with(params);
    }
   
}
