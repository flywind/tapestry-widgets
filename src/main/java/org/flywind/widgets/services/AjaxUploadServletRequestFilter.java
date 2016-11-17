package org.flywind.widgets.services;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.HttpServletRequestHandler;

/**
 * <p>AjaxUploadServletRequest过滤器</p>
 * 
 * @author flywind(飞风)
 * @date 2015年11月20日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class AjaxUploadServletRequestFilter implements HttpServletRequestFilter {

    private AjaxUploadDecoder decoder;

    public AjaxUploadServletRequestFilter(AjaxUploadDecoder decoder) {

        this.decoder = decoder;
    }

    public boolean service(HttpServletRequest request, HttpServletResponse response, HttpServletRequestHandler handler) throws IOException {

        if (InternalUtils.isNonBlank(request.getHeader(AjaxUploadDecoderImpl.AJAX_UPLOAD_HEADER))) {
            decoder.setupUploadedFile(request);
        }

        return handler.service(request, response);
    }

}
