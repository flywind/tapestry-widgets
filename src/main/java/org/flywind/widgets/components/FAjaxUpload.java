package org.flywind.widgets.components;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentEventCallback;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.internal.util.Holder;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.PartialMarkupRenderer;
import org.apache.tapestry5.services.PartialMarkupRendererFilter;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.upload.services.MultipartDecoder;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.apache.tapestry5.util.TextStreamResponse;
import org.flywind.widgets.WidgetSymbolConstants;
import org.flywind.widgets.base.AbstractExtendableComponent;
import org.flywind.widgets.services.AjaxUploadDecoder;
import org.flywind.widgets.utils.JQueryUtils;

/**
 * <p>文件上传组件</p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月23日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
@Events( { WidgetSymbolConstants.AJAX_UPLOAD, WidgetSymbolConstants.NON_XHR_UPLOAD } )
@Import(stylesheet = {"${widget.plugins.assets.path}/jquery/ui/fileupload/fileuploader.css",
		"${widget.plugins.assets.path}/artdialog/skin/default.css"})
public class FAjaxUpload extends AbstractExtendableComponent {

    /**
     * zone回调.
     * JSON 响应 { UPDATE_ZONE_CALLBACK : { url : /your_event_callback_url/, params : /any_custom_params/ } }
     * 
     * en *
     * Event zone call back
     * JSON response { UPDATE_ZONE_CALLBACK : { url : /your_event_callback_url/, params : /any_custom_params/ } }
     */
    public static final String UPDATE_ZONE_CALLBACK = "updateZone";

    /**
     * 文件单位
     * K=KB,M=MB,G=GB
     * 
     * en *
     * File unti
     * K=KB,M=MB,G=GB
     */
    private static final String[] UNITS = new String[] {"K", "M", "G"};

    /**
     * (可选, 默认: false)
     * multiple为true时支持多文件上传.
     * 
     * en *
     * If true, support multi file upload.Default:false
     */
    @Parameter(value = "false")
    private boolean multiple;

    /**
     * (可选, 默认是所有格式)
     * 定义上传格式.
     * 
     * en *
     * Allowed upload file format
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String allowedExtensions;

    /**
     * (可选, 默认: 0 = 无限制)
     * 文件大小限制.
     * 
     * en *
     * File size limit, 0 = No size limit
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "0")
    private String sizeLimit;

    /**
     * (可选, 默认: 3)
     * 限制并行上传量.
     * 
     * en *
     * File upload max connections
     */
    @Parameter(value = "3")
    private int maxConnections;
    
    /**
     * 上传按钮的文字
     * 
     * en *
     * Upload button label
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String uploadLabel;

    /**
     * 上传参数
     * 
     * en *
     * Upload file params
     */
    @Parameter
    private JSONObject params;
    
    /**
     * 上传按钮样式
     * 
     * en *
     * Upload button css class name
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "btn btn-sm btn-info btn-flat")
    private String cls;
    
    /**
     * 上传按钮图标样式
     * 
     * en *
     * Upload button icon css class name
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "fa fa-upload mr5")
    private String itemcls;

    /**
     * 需要传递的上下文
     * 
     * en *
     * Context
     */
    @Parameter
    private Object[] context;
    
    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private ComponentResources resources;

    @Inject
    private MultipartDecoder multipartDecoder;

    @Inject
    private AjaxUploadDecoder ajaxDecoder;

    @Inject
    private Request request;

    @Inject
    private Messages messages;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    void setupRender() {
        setDefaultMethod("uploadable");
    }

    void afterRender() {

        if (params == null)
            params = new JSONObject();

        final JSONObject uploadMessages = new JSONObject()
                .put("typeError", messages.get("typeError"))
                .put("sizeError", messages.get("sizeError"))
                .put("minSizeError", messages.get("minSizeError"))
                .put("emptyError", messages.get("emptyError"))
                .put("onLeave", messages.get("onLeave"))
                .put("uploadLabel", uploadLabel != null ? uploadLabel : messages.get("upload-label"))
                .put("dropAreaLabel", messages.get("dropArea-label"))
                .put("cancelLabel", messages.get("cancel-label"))
                .put("failedLabel", messages.get("failed-label"))
                .put("multipleError", messages.get("multipleError"));

        final long sizeLimit = calculateSizeLimit();

        final JSONObject parameter = new JSONObject()
                .put("elementId", getClientId())
                .put("action", resources.createEventLink(WidgetSymbolConstants.FILE_UPLOAD, context).toURI())
                .put("messages", uploadMessages)
                .put("multiple", multiple)
                .put("sizeLimit", sizeLimit)
                .put("maxConnections", maxConnections)
        		.put("cls", cls)
        		.put("itemcls", itemcls);

        if (allowedExtensions != null) {

            parameter.put("allowedExtensions", new JSONArray(allowedExtensions));
        }

        JQueryUtils.merge(parameter, params);

        javaScriptSupport.require("inits/init-ajaxupload").invoke("ajaxupload").with(parameter);
    }

    /**
     * 限制文件大小.
     * 
     * en *
     * File size limit
     */
    private long calculateSizeLimit() {

        if (sizeLimit.matches("^[\\d]+$")) {

            return Long.valueOf(this.sizeLimit);
        }

        double size = Double.valueOf(sizeLimit.substring(0, sizeLimit.length() - 1));
        int i;

        for (i = 0; i < UNITS.length; i++) {

            size = size * 1024;

            if (sizeLimit.endsWith(UNITS[i])) {

                return (long) size;
            }
        }

        return 0;
    }

    private UploadedFile getUploadedFile() {

        if (ajaxDecoder.isAjaxUploadRequest(request)) {

            return ajaxDecoder.getFileUpload();
        }

        return multipartDecoder.getFileUpload(WidgetSymbolConstants.FILE_UPLOAD_PARAMETER);
    }

    @OnEvent(value = WidgetSymbolConstants.FILE_UPLOAD)
    Object onUpload(EventContext ctx) {


        UploadedFile uploaded = getUploadedFile();

        if (uploaded != null && StringUtils.isEmpty(uploaded.getFileName())) {
            uploaded = null;
        }

        final Holder<Object> holder = Holder.create();
        final ComponentEventCallback<Object> callback = new ComponentEventCallback<Object>() {

            public boolean handleResult(final Object result) {

                holder.put(result);

                return true;
            }
        };

        final boolean success = uploaded != null;

        if ( ! ajaxDecoder.isAjaxUploadRequest(request)) {
        	
            this.resources.triggerEvent(WidgetSymbolConstants.NON_XHR_UPLOAD, ArrayUtils.addAll(new Object[]{ uploaded}, ctx.toStrings()), callback);

            return processNonXHRResult(success, holder.get());
        }

        this.resources.triggerEvent(WidgetSymbolConstants.AJAX_UPLOAD, ArrayUtils.addAll(new Object[]{uploaded}, ctx.toStrings()), callback);
        return processXHRResult(success, holder.get());
    }

    private Object processXHRResult(final boolean success, final Object triggerResult) {

        final JSONObject result = new JSONObject().put("success", success);
        if (triggerResult != null && triggerResult instanceof JSONObject) {

            JQueryUtils.merge(result, (JSONObject) triggerResult);
            return result;
        }

        ajaxResponseRenderer.addFilter(new PartialMarkupRendererFilter() {

            public void renderMarkup(MarkupWriter writer, JSONObject reply, PartialMarkupRenderer renderer) {

                renderer.renderMarkup(writer, reply);
                JQueryUtils.merge(reply, result);
            }
        });

        return triggerResult;
    }

    private Object processNonXHRResult(boolean success, final Object triggerResult) {

        final JSONObject result = new JSONObject().put("success", success);
        if (triggerResult != null && triggerResult instanceof JSONObject) {

            JQueryUtils.merge(result, (JSONObject) triggerResult);
        }

        return new TextStreamResponse("text/html", result.toCompactString());
    }
  
}
