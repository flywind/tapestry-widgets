package org.flywind.widgets.test.base;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;


/**
 * <p>基础页面</p>
 * 
 * @author flywind(飞风)
 * @date 2015年10月13日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class AppBase {

	/**
	 * 应用程序全局参数
	 */
	@Inject
    protected ApplicationGlobals applicationGlobals;
	
	/**
	 * tapestry IOC请求，如获取http的请求
	 */
	@Inject
    protected Request request;
	
	/**
	 * javascript 提供者，用来view层与control层的js交互
	 */
	@Inject
	protected JavaScriptSupport javaScriptSupport;
	
	/**
	 * 组件资源，使用组件的资源触发器
	 */
	@Inject
	protected ComponentResources componentResources;
	
	/**
	 * ajax请求渲染器
	 */
	@Inject
    protected AjaxResponseRenderer ajaxResponseRenderer;
	
	/**
	 * 客户端元素、多用来获取dmo元素属性，如ID
	 */
	@InjectContainer
	protected ClientElement clientElement;
	
	/**
	 * 本地化信息源
	 */
	@Inject
	protected Messages messages;
	
	/**
	 * tapestry alert
	 */
	@Inject
	protected AlertManager alertManager;
	
	/**
     * 获取分页对象
     * 指定一页显示数量 注意：指定pageSize时也要指定分页组件（t:type="EasyDataGrid"）的t:pageSize要相同。
     * @return
     */
   /* public FPage getPage() {
        int pageNumber = 1;
        int pageSize = 10;
        Object opageNumber = request.getAttribute("pageNumber");
        Object opageSize = request.getAttribute("pageSize");
        if(opageNumber != null) {
        	pageNumber = (Integer)opageNumber;
        }
        if(opageSize != null) {
        	pageSize = (Integer)opageSize;
        }
        return new FPage(pageNumber, pageSize);
    }*/
}
