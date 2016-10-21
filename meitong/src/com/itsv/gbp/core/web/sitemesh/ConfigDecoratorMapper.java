package com.itsv.gbp.core.web.sitemesh;

import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.module.sitemesh.Config;
import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.DecoratorMapper;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.mapper.AbstractDecoratorMapper;
import com.opensymphony.module.sitemesh.mapper.ConfigLoader;

/**
 * ˵���������õ�װ��ӳ���ࡣ��Ҫ�����������⣺ <br>
 * ��spring�У��û�����Ŀ�����xxx.do����ʽ���������ʾ���û�����yyy.jspҳ�档 <br>
 * ������ϣ������yyy.jspҳ�棬����������(xxx.do)����ȷ��Ӧ�ú���װ�Ρ� <br>
 * jsp�ļ��ľ����ַ��request���������á��������ƿ���ͨ�����ò��� requestAttribute ����ָ����
 * 
 * @author admin 2005-1-10
 * @see com.opensymphony.module.sitemesh.mapper.ConfigDecoratorMapper
 */
public class ConfigDecoratorMapper extends AbstractDecoratorMapper {
    /**
     * logger����
     */
    protected static final Log logger = LogFactory.getLog(ConfigDecoratorMapper.class);

    private ConfigLoader configLoader = null; //װ����

    private String requestAttribute = null; //���jsp·����Ϣ��request������

    /**
     * ��ʼ������
     */
    public void init(Config config, Properties properties, DecoratorMapper parent)
                    throws InstantiationException {
        super.init(config, properties, parent);
        try {
            //·����ϢĬ�ϴ������Ϊ"request_jsp_location"��������
            requestAttribute = properties.getProperty("requestAttribute", "request_jsp_location");

            String fileName = properties.getProperty("config", "/WEB-INF/decorators.xml");
            configLoader = new ConfigLoader(fileName, config);
        } catch (Exception e) {
            logger.error("sitemesh��ʼ��ʧ�ܣ�" + e.toString());
            throw new InstantiationException(e.toString());
        }
        logger.info("sitemesh��ʼ���ɹ���");
    }

    /**
     * ����Ҫ��ʾ��jsp�ļ�·�����õ���Ӧ��װ������
     */
    public Decorator getDecorator(HttpServletRequest request, Page page) {
        String jspPath = (String) request.getAttribute(requestAttribute);
        if (jspPath == null) {
            //���û�и����ԣ���ȡʵ�������jsp��ַ
            //���������û�ֱ����jsp��������
            jspPath = request.getServletPath();
        }

        String name = null;
        try {
            name = configLoader.getMappedName(jspPath);
        } catch (ServletException e) {
            e.printStackTrace();
        }

        if (logger.isDebugEnabled()) {
            logger.debug("����jsp[" + jspPath + "].�ҵ�decorator��[" + name + "]");
        }

        Decorator result = getNamedDecorator(request, name);
        return result == null ? super.getDecorator(request, page) : result;
    }

    /**
     * ��������������װ���������ƣ��õ�װ������
     */
    public Decorator getNamedDecorator(HttpServletRequest request, String name) {
        Decorator result = null;
        try {
            result = configLoader.getDecoratorByName(name);
        } catch (ServletException e) {
            e.printStackTrace();
        }

        if (result == null || (result.getRole() != null && !request.isUserInRole(result.getRole()))) {
            // if the result is null or the user is not in the role
            return super.getNamedDecorator(request, name);
        } else {
            return result;
        }
    }

}