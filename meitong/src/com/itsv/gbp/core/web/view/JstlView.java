package com.itsv.gbp.core.web.view;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.JstlUtils;
import org.springframework.web.servlet.view.InternalResourceView;

/**
 * ˵������spring�ṩ�� JstlView ��Ĺ��ܼ�ǿ�� <br>
 * ��Ҫ���򵽵�jspҳ���ַ�����request�������У��ṩ��siteMeshʹ�á�
 * 
 * @author admin 2005-1-10
 * 
 * @see org.springframework.web.servlet.view.JstlView
 */
public class JstlView extends InternalResourceView {
    private static String REQUEST_JSP_LOCATION = "request_jsp_location";

    protected void exposeHelpers(HttpServletRequest request) throws Exception {
        //��jsp�ļ��ĵ�ַ���õ�request��������
        request.setAttribute(REQUEST_JSP_LOCATION, getUrl());
        //Ϊjstlҳ���ṩ���ػ���Ϣ��������ʱ�ò���
        JstlUtils.exposeLocalizationContext(request, getApplicationContext());
    }

}