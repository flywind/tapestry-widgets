package org.flywind.widgets.test.pages;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.PartialMarkupRenderer;
import org.apache.tapestry5.services.PartialMarkupRendererFilter;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.upload.services.MultipartDecoder;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.apache.tapestry5.util.TextStreamResponse;
import org.flywind.widgets.WidgetSymbolConstants;
import org.flywind.widgets.utils.JQueryUtils;

import net.sf.json.JSONException;

public class KissyEditorUploadHander {
	@SuppressWarnings("unused")
	@Inject
	private Context context;

	private String outUrl;

	@Inject
	private ApplicationGlobals applicationGlobals;

	@Inject
	private RequestGlobals requestGlobals;

	@SuppressWarnings("unused")
	@Property
	private UploadedFile file;

	@Inject
	private MultipartDecoder decoder;
	
	@Inject
	private Request request;
	
	@Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

	void onActivate() throws Exception {
		
		

		UploadedFile file = decoder.getFileUpload("Filedata");

		System.out.println(request.isXHR());
		
		// 文件保存目录路径
		String savePath = applicationGlobals.getServletContext().getRealPath("/uploadImages/") + "\\";

		// 文件保存目录UR
		String saveUrl = getRequest().getContextPath() + "/uploadImages/";

		// 最大文件大小
		long maxSize = 1048576;// 1M

		// 检查目录
		File uploadDir = new File(savePath);

		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		// 检查目录写权限
		if (!uploadDir.canWrite()) {
			System.out.println(getError("上传目录没有写权限。"));
			return;
		}

		String dirName = "work";
		// 创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}

		String fileName = file.getFileName();

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_" + fileName;

		File copied = new File(savePath + newFileName);

		long fileSize = file.getSize();

		if (fileSize > maxSize) {
			System.out.println(getError("上传文件大小超过限制。"));
			return;
		}

		file.write(copied);

		System.out.println(saveUrl);
		
		HttpServletResponse response = requestGlobals.getHTTPServletResponse();
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = null;  
        try {  
        	String url = saveUrl + newFileName;
        	/*JSONObject json = new JSONObject();  
            json.put("imgUrl", url);
        	processXHRResult(0,json);*/
        	writer = response.getWriter();
            JSONObject json = new JSONObject();  
            json.put("status", 0);
            json.put("imgUrl", url);
            writer.println(json);  
        } catch (JSONException e) {  
        	JSONObject json = new JSONObject();  
            json.put("status", 1);
            json.put("error", "对不起，文件上传失败!!请重试..");
        	writer.println(json);  
            //e.printStackTrace();  
        } finally {
			InternalUtils.close(writer);
		}

		/*try {
			String url = saveUrl + newFileName;
			setOutUrl("{\"status\":\"0\" , \"imgUrl\" :\"" + url + "\"}");
		} catch (Exception e) {
			setOutUrl("{\"status\":\"1\" , \"error\" :\" error \"}");
		}*/

	}
	
	private Object processXHRResult(int status, final Object triggerResult) {

        final JSONObject result = new JSONObject().put("status", status);
        if (triggerResult != null && triggerResult instanceof JSONObject) {

            JQueryUtils.merge(result, (JSONObject) triggerResult);
            //return result;
        }
        return new TextStreamResponse("application/json", result.toCompactString());
        /*ajaxResponseRenderer.addFilter(new PartialMarkupRendererFilter() {

            public void renderMarkup(MarkupWriter writer, JSONObject reply, PartialMarkupRenderer renderer) {

                renderer.renderMarkup(writer, reply);
                JQueryUtils.merge(reply, result);
            }
        });

        return triggerResult;*/
        
       // return null;
    }

	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toString();
	}

	protected final HttpServletRequest getRequest() {
		return requestGlobals.getHTTPServletRequest();
	}

	public String getOutUrl() {
		return outUrl;
	}

	public void setOutUrl(String outUrl) {
		this.outUrl = outUrl;
	}

}
