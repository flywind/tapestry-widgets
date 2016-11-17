package org.flywind.widgets.test.pages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.OutputRaw;
import org.apache.tapestry5.internal.util.Holder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONCollection;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.PartialMarkupRenderer;
import org.apache.tapestry5.services.PartialMarkupRendererFilter;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.upload.services.MultipartDecoder;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.flywind.widgets.WidgetSymbolConstants;
import org.flywind.widgets.services.AjaxUploadDecoder;
import org.flywind.widgets.utils.JQueryUtils;

import net.sf.json.JSONException;

public class UploadHander {

	private JSONObject outUrl; 

	@Inject
	private ApplicationGlobals applicationGlobals;

	@Inject
	private RequestGlobals requestGlobals;
	
	@Inject
	private MultipartDecoder decoder;
	
	@Inject
	private Request request;
	
	@Inject
    private MultipartDecoder multipartDecoder;

    @Inject
    private AjaxUploadDecoder ajaxDecoder;
    
    @Inject
    private ComponentResources componentResources;

	void onActivate() throws Exception {
		
		// 文件保存目录路径
		String savePath = applicationGlobals.getServletContext().getRealPath("/editorUploads/") + "\\";

		// 文件保存目录UR
		String saveUrl = getRequest().getContextPath() + "/editorUploads/";
		
		File uploadDir = new File(savePath);
        if(!uploadDir.exists()){
        	uploadDir.mkdirs();
        }
		
		Map<String,Object> fileInfo = JQueryUtils.getFileInfo(getRequest());
		
		String oldFilename = (String)fileInfo.get("filename");//原文件名
		String fileSuffix = (String)fileInfo.get("suffix");//文件格式
		
		
		
		try {
			//获取上传文件的输入流
			InputStream in = getRequest().getInputStream();
			//创建一个文件输出流
			FileOutputStream out = new FileOutputStream(savePath + "\\" + oldFilename);
			//创建一个缓冲区
	        byte[] buffer = new byte[1024];
	        //判断输入流中的数据是否已经读完的标识
	        int len = 0;
	      //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
	        while((len=in.read(buffer))>0){
	            //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
	            out.write(buffer, 0, len);
	       }
            //关闭输入流
  	      in.close();
  	      out.flush();
  	      //关闭输出流
  	      out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		

        
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
		/*String err = "";
		//String url = "http://localhost:666"+saveUrl + oldFilename;
		String url = saveUrl + oldFilename;
		String loc = "http://127.0.0.1:666";
		
		
		
		
		HttpServletResponse response = requestGlobals.getHTTPServletResponse();
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = null;  */
        /*try {  
        	//JSONObject msg = new JSONObject().put("url", url).put("localname", loc).put("id", '1');
        	//;
        	writer = response.getWriter();
            JSONObject json = new JSONObject();  
            json.put("err", err);
            json.put("msg", url);
            writer.write(json.toCompactString());  
            //new StatusResponse(json.toString());
            //JSONCollection j = new JSONCollection(); 
            //json.print(writer);
        } catch (JSONException e) {  
        	//out.append("{\"err\":\"对不起，文件上传失败!!请重试..\"}");  
            //e.printStackTrace();  
        } finally {
        	writer.flush();
        	writer.close();
			//InternalUtils.close(writer);
		}*/
		/*try {
        	setOutUrl("{\"err\":\""+err+"\",\"msg\":{\"url\":\""+url+"\",\"localname\":\""+loc+"\",\"id\":\"1\"}}");
		} catch (Exception e) {
			// TODO: handle exception
		}*/
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
		
		HttpServletResponse response = requestGlobals.getHTTPServletResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		//PrintWriter writer = null;
        String url = "http://localhost:666"+saveUrl + oldFilename;
		try {
			//writer = response.getWriter();
			JSONObject json = new JSONObject();  
            json.put("err", "");
            json.put("msg", url);
            setOutUrl(json);
			//writer.print(json.toString());
		} catch (Exception e) {
			//e.printStackTrace();
		} finally {
			//InternalUtils.close(writer);
		}
		
	}
	
	
	protected final HttpServletRequest getRequest() {
		return requestGlobals.getHTTPServletRequest();
	}
	
	protected final HttpServletResponse getResponse() {
		return requestGlobals.getHTTPServletResponse();
	}

	public JSONObject getOutUrl() {
		return outUrl;
	}

	public void setOutUrl(JSONObject outUrl) {
		this.outUrl = outUrl;
	}
	
	
}
