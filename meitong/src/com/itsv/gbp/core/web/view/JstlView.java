package com.itsv.gbp.core.web.view;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.JstlUtils;
import org.springframework.web.servlet.view.InternalResourceView;

/**
 * 说明：对spring提供的 JstlView 类的功能加强。 <br>
 * 将要导向到的jsp页面地址存放在request的属性中，提供给siteMesh使用。
 * 
 * @author admin 2005-1-10
 * 
 * @see org.springframework.web.servlet.view.JstlView
 */
public class JstlView extends InternalResourceView {
    private static String REQUEST_JSP_LOCATION = "request_jsp_location";

    protected void exposeHelpers(HttpServletRequest request) throws Exception {
        //将jsp文件的地址放置到request的属性里
        request.setAttribute(REQUEST_JSP_LOCATION, getUrl());
        //为jstl页面提供本地化信息。我们暂时用不上
        JstlUtils.exposeLocalizationContext(request, getApplicationContext());
    }

}