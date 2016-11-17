package org.flywind.widgets.test.pages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.flywind.widgets.WidgetSymbolConstants;
import org.flywind.widgets.components.FAjaxUpload;
import org.flywind.widgets.test.base.AppBase;

@Import(stylesheet="context:assets/styles/demo.css")
public class FAjaxUploadTest extends AppBase {
	
	@Persist(PersistenceConstants.FLASH)
    @Property
    private String message;

    @Persist
    @Property
    private List<UploadedFile> uploadedFiles;
    
    @Persist(PersistenceConstants.FLASH)
    @Property
    private UploadedFile uploadedFile;

    @InjectComponent
    private Zone uploadResult,uploadImg,uploadImgNoAjax;

    @Inject
    private AjaxResponseRenderer renderer;
    
    void onActivate() {
        if (uploadedFiles == null)
            uploadedFiles = new ArrayList<UploadedFile>();
    }

    @OnEvent(component = "uploadImage", value = WidgetSymbolConstants.AJAX_UPLOAD)
    void onImageUpload(UploadedFile uploadedFile) {
        this.uploadedFiles.add(uploadedFile);  
        renderer.addRender("uploadResult", uploadResult.getBody());
    }
    
    @OnEvent(component = "uploadImageTwo", value = WidgetSymbolConstants.AJAX_UPLOAD)
    void onImageUploadTwo(UploadedFile uploadedFile) {	
    	File copied = new File("d:\\" + uploadedFile.getFileName());
        if (uploadedFile != null) {
            uploadedFile.write(copied);
        }
        this.uploadedFile = uploadedFile;
        renderer.addRender("uploadImg", uploadImg.getBody());
    }
    
    //为了支持ie
    @OnEvent(component = "uploadImageTwo", value = WidgetSymbolConstants.NON_XHR_UPLOAD)
    Object onImageUploadTwoNoAjax(UploadedFile uploadedFile) {	
    	File copied = new File("d:\\" + uploadedFile.getFileName());
        if (uploadedFile != null) {
            uploadedFile.write(copied);
        }
        this.uploadedFile = uploadedFile;
        final JSONObject result = new JSONObject();
        final JSONObject params = new JSONObject()
        		.put("url", componentResources.createEventLink("myEvent", "NON_XHR__UPLOAD").toURI())
        		.put("zoneId", "uploadImgNoAjax");

        result.put(FAjaxUpload.UPDATE_ZONE_CALLBACK, params);

        return result;
    }

    void onMyEvent(String messages){
    	message = "文件上传异常: " + messages;
        renderer.addRender("uploadImgNoAjax", uploadImgNoAjax.getBody());
    }
    
    void onUploadException(FileUploadException ex) {
        message = "文件上传异常: " + ex.getMessage();
        renderer.addRender("uploadImg", uploadImg.getBody());
        renderer.addRender("uploadResult", uploadResult.getBody());
    }
}
