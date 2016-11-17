package org.flywind.widgets.test.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.upload.services.MultipartDecoder;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.flywind.widgets.utils.JQueryUtils;

import net.sf.json.JSONException;

public class UploadHander2 {

	private String outUrl; 

	@Inject
	private ApplicationGlobals applicationGlobals;

	@Inject
	private RequestGlobals requestGlobals;
	
	@Inject
	private MultipartDecoder decoder;

	void onActivate() throws Exception {
		
		// 文件保存目录路径
		String savePath = applicationGlobals.getServletContext().getRealPath("/editorUploads/") + "\\";

		// 文件保存目录UR
		String saveUrl = getRequest().getContextPath() + "/editorUploads/";
		
		File uploadDir = new File(savePath);
        if(!uploadDir.exists()){
        	uploadDir.mkdirs();
        }
		
		UploadedFile file = decoder.getFileUpload("filedata");
		
		String fileName = file.getFileName();
		 
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_"+fileName;
		 
		File copied = new File(savePath + newFileName);
		 
		long fileSize = file.getSize();
	 
		file.write(copied);
		

        
     // {'err':'','msg':{'url':'!upload\/day_110131\/20110131164055468.jpg','localname':'Thunder.jpg','id':'1'}}
       /* Object err = null;
        String url = "http://localhost:666"+saveUrl + oldFilename;
		JSONObject json = new JSONObject();
		json.put("err", err == null ? "" : err);
		if (url != null) {
			JSONObject json2 = new JSONObject();
			json2.put("url", url);
			json.put("msg", json2);
		}*/
		String err = "";
		//String url = "http://localhost:666"+saveUrl + oldFilename;
		String url = saveUrl + newFileName;
		String loc = "http:127.0.0.1:666";
    	/*JSONObject msg = new JSONObject().put("url", newFileName).put("localname", loc).put("id", '1');
        JSONObject json = new JSONObject().put("err", err).put("msg", msg); */
        try {
        	setOutUrl("{\"err\":\""+err+"\",\"msg\":{\"url\":\""+newFileName+"\",\"localname\":\""+loc+"\",\"id\":\"1\"}}");
		} catch (Exception e) {
			// TODO: handle exception
		}
        
		/*JSONObject json = new JSONObject();
		json.put("err", err == null ? "" : err);
		if (url != null) {
			json.put("err", err);
			json.put("msg", url);
		}*/
		/*HttpServletResponse response = requestGlobals.getHTTPServletResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		printInfo(response, err, url);*/
		
		
		/*try {
			setOutUrl(json.toString());
			//setOutUrl("{\"status\":\"0\" , \"imgUrl\" :\"" + url + "\"}");
		} catch (Exception e) {
			//setOutUrl("{\"status\":\"1\" , \"error\" :\" error \"}");
		}*/
		
		/*HttpServletResponse response = requestGlobals.getHTTPServletResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			InternalUtils.close(writer);
		}*/
		
		

	}
	
	public void printInfo(HttpServletResponse response, String err, String newFileName) throws IOException {  
  
        PrintWriter writer = response.getWriter();  
        try {  
        	String loc = "http:127.0.0.1:666";
        	JSONObject msg = new JSONObject().put("url", newFileName).put("localname", loc).put("id", '1');
            JSONObject json = new JSONObject().put("err", err).put("msg", msg);  
            writer.print(json);  
        } catch (JSONException e) {  
        	writer.print("{\"err\":\"对不起，文件上传失败!!请重试..\"}");  
            e.printStackTrace();  
        } finally {
        	writer.flush();
        	writer.close();
			//InternalUtils.close(writer);
		}
    }  

	protected final HttpServletRequest getRequest() {
		return requestGlobals.getHTTPServletRequest();
	}
	
	protected final HttpServletResponse getResponse() {
		return requestGlobals.getHTTPServletResponse();
	}

	public String getOutUrl() {
		return outUrl;
	}

	public void setOutUrl(String outUrl) {
		this.outUrl = outUrl;
	}
	
	
}
