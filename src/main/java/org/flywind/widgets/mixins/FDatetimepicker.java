package org.flywind.widgets.mixins;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.flywind.widgets.utils.JQueryUtils;

@Import(stylesheet={"${widget.plugins.assets.path}/datetimepicker/jquery.datetimepicker.css"})
public class FDatetimepicker{

	@Parameter
	private JSONObject params;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @InjectContainer
    private ClientElement element;

    @Inject
    private ComponentResources resources;
    
    public void afterRender(){
    	JSONObject data = new JSONObject();
    	data.put("id", element.getClientId());
    	
    	if(params == null){
    		params = new JSONObject();
    	}
    	
    	JSONObject defaults = new JSONObject();
    	defaults.put("lang", "ch");//语言选择中文
    	defaults.put("format", "Y-m-d");//格式化日期Y-m-d H:i
    	defaults.put("timepicker", false);//是开启时间选项
    	defaults.put("datepicker", true);//是否开启日期选型
    	defaults.put("yearStart", "2000");//设置最小年份
    	defaults.put("yearEnd", "2050");//设置最大年份
    	defaults.put("todayButton", true);//是否开启选择今天按钮
    	
    	JQueryUtils.merge(defaults, params);
    	data.put("params", defaults);
    	configure(data);
    	
    	javaScriptSupport.require("inits/init-fDatetimepicker").with(data);
    }
	
    protected void configure(JSONObject params){};
}
