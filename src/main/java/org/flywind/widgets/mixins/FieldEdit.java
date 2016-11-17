package org.flywind.widgets.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * <p>字段编辑组件</p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月26日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class FieldEdit {
	
	@Parameter(name = "clientEvent", value="blur", defaultPrefix = BindingConstants.LITERAL)
    private String clientEvent;

    @Parameter(name = "event", defaultPrefix = BindingConstants.LITERAL, required = true)
    private String event;

    @Parameter(name = "prefix", defaultPrefix = BindingConstants.LITERAL, value = "default")
    private String prefix;

    @Parameter(name = "context")
    private Object[] context;

    @Parameter(name = "zone", defaultPrefix = BindingConstants.LITERAL, required = true)
    private String zone;

    @Parameter(name = "secure", defaultPrefix = BindingConstants.LITERAL, value = "false")
    private boolean secure;
    
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String tableName;
    
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String field;
    
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String customerCode;

    @Inject
    private ComponentResources componentResources;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @InjectContainer
    private ClientElement clientElement;

    void afterRender() {
        String listenerURI = componentResources.createEventLink(event, context).toAbsoluteURI(secure);
        JSONObject data = new JSONObject();
        data.put("id", clientElement.getClientId());
        data.put("clientEvent", clientEvent);
        data.put("listenerURI", listenerURI);
        data.put("zoneElementId", zone);
        data.put("tableName", tableName);
        data.put("field", field);
        data.put("customerCode", customerCode);
        
        javaScriptSupport.require("inits/init-fieldEdit").with(data);
    }
}
