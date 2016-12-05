package org.flywind.widgets.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.Core;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.javascript.JavaScriptModuleConfiguration;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.ModuleManager;
import org.apache.tapestry5.services.javascript.StackExtension;
import org.apache.tapestry5.services.javascript.StackExtensionType;
import org.flywind.widgets.WidgetSymbolConstants;

/**
 * <p>tapestry ioc配置</p>
 * 
 * @author flywind(飞风)
 * @date 2015年10月30日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class WidgetModule
{
	/**
     * 服务的绑定，绑定之后可以使用@Inject注入，在项目中调用
     * @param binder 服务绑定器
     */
    public static void bind(ServiceBinder binder)
    {
    	//ajax上传的服务
    	binder.bind(AjaxUploadDecoder.class, AjaxUploadDecoderImpl.class).scope(ScopeConstants.PERTHREAD);
    }
    
    /**
     * 提供工厂默认配置，也可自己定义
     * @param configuration IOC配置
     */
    @Contribute(SymbolProvider.class)
	@FactoryDefaults
	public static void contributeFactoryDefaults(
			MappedConfiguration<String, Object> configuration) {
    	//定义资源路径
        configuration.add(WidgetSymbolConstants.WIDGET_PLUGINS_PATH, "classpath:/META-INF/modules/plugins");
        configuration.add(WidgetSymbolConstants.WIDGET_PLUGINS_ASSETS_PATH, "classpath:/META-INF/assets/plugins");
        configuration.add(WidgetSymbolConstants.WIDGET_MODULES_PATH, "classpath:/META-INF/modules");
   
        //If true,use free icons,like font-awesome etc.Default true
        configuration.add(WidgetSymbolConstants.IS_USE_FREE_ICONS, true);
        
        //If true,use easyui plugin style.Default false
        configuration.add(WidgetSymbolConstants.IS_USE_EASYUI_CSS, false);
    }
    
    public static void contributeApplicationDefaults(
            MappedConfiguration<String, Object> configuration)
    {
    	//定义jQuery模块的名称
        configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");
        //压缩JS，在开发模式建议设为false方便开发测试
        //configuration.add(SymbolConstants.MINIFICATION_ENABLED, true);
        
    }
    
    /**
     * 组件名称定义
     * @param configuration
     */
    public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
	{
		configuration.add(new LibraryMapping("widgets", "org.flywind.widgets"));
	}
    
    @Contribute(JavaScriptStack.class)
    @Core
    public static void setupInternalJavaScriptStack(OrderedConfiguration<StackExtension> configuration, 
    		@Symbol(WidgetSymbolConstants.IS_USE_FREE_ICONS) boolean icons,
    		@Symbol(WidgetSymbolConstants.IS_USE_EASYUI_CSS) boolean easyui)
    {
    	String fontAwesomePath = "${" + WidgetSymbolConstants.WIDGET_PLUGINS_ASSETS_PATH + "}/font-awesome/css/font-awesome.min.css";
    	String themifyIconsPath = "${" + WidgetSymbolConstants.WIDGET_PLUGINS_ASSETS_PATH + "}/themify-icons/themify-icons.min.css";
    	String ionIconsPath = "${" + WidgetSymbolConstants.WIDGET_PLUGINS_ASSETS_PATH + "}/ion-icons/css/ionicons.min.css";
    	if(icons){
    		addIconsStylesheets(configuration,fontAwesomePath,themifyIconsPath,ionIconsPath);
    	}
        
    	String easyuiPath = "${" + WidgetSymbolConstants.WIDGET_PLUGINS_ASSETS_PATH + "}/easyui/themes/bootstrap/easyui.css";
    	if(easyui){
    		addEasyuiStylesheets(configuration,easyuiPath);
    	}
    }
    
    private static void addIconsStylesheets(OrderedConfiguration<StackExtension> configuration, 
    		String fontAwesome, String themifyIcons, String ionIcons)
    {
        add(configuration, StackExtensionType.STYLESHEET,fontAwesome);
        add(configuration, StackExtensionType.STYLESHEET,themifyIcons);
        add(configuration, StackExtensionType.STYLESHEET,ionIcons);
    }
    
    private static void addEasyuiStylesheets(OrderedConfiguration<StackExtension> configuration, String easyui)
    {
        add(configuration, StackExtensionType.STYLESHEET,easyui);
    }
    
    private static void add(OrderedConfiguration<StackExtension> configuration, StackExtensionType type, String... paths)
    {
        for (String path : paths)
        {
            int slashx = path.lastIndexOf('/');
            String id = path.substring(slashx + 1);

            configuration.add(id, new StackExtension(type, path));
        }
    }
    
    
    /**
     * 贡献组件静态资源目录，组件开发时未贡献的目录在项目中无法调用
     * @param configuration
     */
    public static void contributeClasspathAssetAliasManager(
			MappedConfiguration<String, String> configuration) {
		//定义modules资源路径,如果是modules也可以不定义，默认会是modules这个文件夹
		configuration.add("widget.modules.path", "META-INF/modules");
	}
    
    /**
     * requireJS shim---注册jQuery模块
     */
    @Contribute(ModuleManager.class)
	public static void setupComponentsShims(
			MappedConfiguration<String, Object> configuration,
			@Inject @Path("${widget.plugins.path}/datatables/jquery.dataTables.js") Resource datatable,
			@Inject @Path("${widget.plugins.path}/jquery/ui/widget.js") Resource widget,
			@Inject @Path("${widget.plugins.path}/jquery/ui/jquery.fileuploader.js") Resource fileupload,
			@Inject @Path("${widget.plugins.path}/jquery/jquery.json-2.4.js") Resource json,
			@Inject @Path("${widget.plugins.path}/datetimepicker/jquery.datetimepicker.js") Resource datetimepicker,
			@Inject @Path("${widget.plugins.path}/easyui/jquery.easyui.min.js") Resource easyui,
			@Inject @Path("${widget.plugins.path}/easyui/datagrid-detailview.js") Resource griddetailview,
			@Inject @Path("${widget.plugins.path}/paginate/bootstrap-paginator.js") Resource pager,
			@Inject @Path("${widget.plugins.path}/artdialog/jquery.artDialog.min.js") Resource artdialog) {

		configuration.add("plugin/datatable",new JavaScriptModuleConfiguration(datatable).dependsOn("jquery"));
		configuration.add("plugin/widget",new JavaScriptModuleConfiguration(widget).dependsOn("jquery"));
		configuration.add("plugin/fileupload",new JavaScriptModuleConfiguration(fileupload).dependsOn("jquery"));
		configuration.add("plugin/json",new JavaScriptModuleConfiguration(json).dependsOn("jquery"));
		configuration.add("plugin/datetimepicker",new JavaScriptModuleConfiguration(datetimepicker).dependsOn("jquery"));
		configuration.add("plugin/easyui",new JavaScriptModuleConfiguration(easyui).dependsOn("jquery"));
		configuration.add("plugin/griddetailview",new JavaScriptModuleConfiguration(griddetailview).dependsOn("jquery","plugin/easyui"));
		configuration.add("plugin/pager",new JavaScriptModuleConfiguration(pager).dependsOn("jquery"));
		configuration.add("plugin/adialog",new JavaScriptModuleConfiguration(artdialog).dependsOn("jquery"));

		
	} 
    
    /**
     * ajax文件上传HttpServletRequestFilter
     * @param configuration
     * @param ajaxUploadDecoder
     */
    public static void contributeHttpServletRequestHandler(
			final OrderedConfiguration<HttpServletRequestFilter> configuration,
			final AjaxUploadDecoder ajaxUploadDecoder) {
		configuration.add("AjaxUploadFilter",new AjaxUploadServletRequestFilter(ajaxUploadDecoder),"after:IgnoredPaths");
	}
    
}
