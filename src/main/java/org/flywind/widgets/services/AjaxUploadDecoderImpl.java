package org.flywind.widgets.services;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.upload.internal.services.UploadedFileItem;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.flywind.widgets.WidgetSymbolConstants;

/**
 * <p>上传解码器方法</p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月20日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class AjaxUploadDecoderImpl implements AjaxUploadDecoder {

    private UploadedFileItem uploadedFile;

    public static final String AJAX_UPLOAD_HEADER = "X-File-Name";

    private FileItemFactory fileItemFactory;

    public AjaxUploadDecoderImpl(FileItemFactory fileItemFactory) {

        this.fileItemFactory = fileItemFactory;
    }

    public boolean isAjaxUploadRequest(HttpServletRequest request) {

        return request.getHeader(AJAX_UPLOAD_HEADER) != null;
    }

    public boolean isAjaxUploadRequest(Request request) {

        return request.isXHR() && request.getHeader(AJAX_UPLOAD_HEADER) != null;
    }

    public void setupUploadedFile(HttpServletRequest request) {

        String fieldName = request.getHeader(AJAX_UPLOAD_HEADER);
        FileItem item = fileItemFactory.createItem(fieldName, request.getContentType(), false, request.getParameter(WidgetSymbolConstants.FILE_UPLOAD_PARAMETER));
        try {
            TapestryInternalUtils.copy(request.getInputStream(), item.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("Could not copy request's input stream to file", e);
        }

        uploadedFile = new UploadedFileItem(item);
    }

    public UploadedFile getFileUpload() {

        return uploadedFile;
    }

}
