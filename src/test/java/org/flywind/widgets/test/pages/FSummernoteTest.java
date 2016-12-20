package org.flywind.widgets.test.pages;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.upload.services.MultipartDecoder;
import org.apache.tapestry5.upload.services.UploadedFile;

public class FSummernoteTest {

	@Property
	@Persist(PersistenceConstants.FLASH)
	private String edit,edit2;
	
	@Inject
	private ComponentResources componentResources;
	
	@Property
	private String uploadurl;
	
	@Inject
	private Request request;
	
	@Inject
	private ApplicationGlobals applicationGlobals;

	@Inject
	private RequestGlobals requestGlobals;
	
	@Property
	private UploadedFile file;
	 
	@Inject
	private MultipartDecoder decoder;
	
	public void setupRender(){
		uploadurl = componentResources.createEventLink("upload").toAbsoluteURI();
	}
	
	public JSONObject onUpload(){
		String fileName = request.getParameter("name");
		String fileSize = request.getParameter("size");
		String fileType = request.getParameter("type");
		//File d = (File)request.getParameter("file");
		
		System.out.println();
		
		//file = decoder.getFileUpload("filedata");
		
		JSONObject result = new JSONObject();
		
		result.put("url", "https://www.baidu.com/img/bd_logo1.png");
		//result = Uploadfile(file,2048);
		
		return result;
	}
	
	public JSONObject Uploadfile(UploadedFile file, int limitSize) {
		// File save path
		String savePath = applicationGlobals.getServletContext().getRealPath("/uploadImages/editor/") + "\\";

		// File save path url
		String saveUrl = getRequest().getContextPath() + "/uploadImages/editor/";

		File uploadDir = new File(savePath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		
		System.out.println("File size:"+file.getSize()/1024 + "K");
		if(file.getSize()/1024 > limitSize){
			JSONObject json = new JSONObject();
			json.put("err", "Upload error, file size can not exceed "+ limitSize +"Kb!");
	        return json;
		}
		
		String fileType = getRequest().getHeader("S-File-Type");
		
		String fileSuffix = fileType.substring(fileType.lastIndexOf("/")+1);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		
		String newFileName = df.format(new Date()) +"."+ fileSuffix;
		
		try {
			
			File copied = new File(savePath, newFileName);
			file.write(copied);
			
			String url = saveUrl + newFileName;
			
			JSONObject json = new JSONObject();  
	        json.put("err", "");
	        json.put("url", url);
	        return json;
			
		} catch (Exception e) {
			JSONObject json = new JSONObject();  
	        json.put("err", "Upload file error!");
	        return json;
		}
			
	}
	
	protected final HttpServletRequest getRequest() {
		return requestGlobals.getHTTPServletRequest();
	}
}
