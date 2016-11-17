package org.flywind.widgets.services;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.upload.services.UploadedFile;

/**
 * <p>上传解码器接口</p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月20日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public interface AjaxUploadDecoder {

    boolean isAjaxUploadRequest(HttpServletRequest request);

    boolean isAjaxUploadRequest(Request request);

    UploadedFile getFileUpload();

    void setupUploadedFile(HttpServletRequest request);

}
