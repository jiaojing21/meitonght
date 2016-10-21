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
 * 说明：可配置的装饰映射类。主要想解决如下问题： <br>
 * 在spring中，用户请求的可能是xxx.do的形式，但最后显示给用户的是yyy.jsp页面。 <br>
 * 而我们希望根据yyy.jsp页面，而不是请求(xxx.do)，来确定应用何种装饰。 <br>
 * jsp文件的具体地址从request的属性里获得。属性名称可以通过配置参数 requestAttribute 进行指定。
 * 
 * @author admin 2005-1-10
 * @see com.opensymphony.module.sitemesh.mapper.ConfigDecoratorMapper
 */
public class ConfigDecoratorMapper extends AbstractDecoratorMapper {
    /**
     * logger对象
     */
    protected static final Log logger = LogFactory.getLog(ConfigDecoratorMapper.class);

    private ConfigLoader configLoader = null; //装载器

    private String requestAttribute = null; //存放jsp路径信息的request属性名

    /**
     * 初始化方法
     */
    public void init(Config config, Properties properties, DecoratorMapper parent)
                    throws InstantiationException {
        super.init(config, properties, parent);
        try {
            //路径信息默认存放在名为"request_jsp_location"的属性里
            requestAttribute = properties.getProperty("requestAttribute", "request_jsp_location");

            String fileName = properties.getProperty("config", "/WEB-INF/decorators.xml");
            configLoader = new ConfigLoader(fileName, config);
        } catch (Exception e) {
            logger.error("sitemesh初始化失败：" + e.toString());
            throw new InstantiationException(e.toString());
        }
        logger.info("sitemesh初始化成功！");
    }

    /**
     * 根据要显示的jsp文件路径，得到对应的装饰器类
     */
    public Decorator getDecorator(HttpServletRequest request, Page page) {
        String jspPath = (String) request.getAttribute(requestAttribute);
        if (jspPath == null) {
            //如果没有该属性，则取实际请求的jsp地址
            //用来处理用户直接用jsp请求的情况
            jspPath = request.getServletPath();
        }

        String name = null;
        try {
            name = configLoader.getMappedName(jspPath);
        } catch (ServletException e) {
            e.printStackTrace();
        }

        if (logger.isDebugEnabled()) {
            logger.debug("处理jsp[" + jspPath + "].找到decorator：[" + name + "]");
        }

        Decorator result = getNamedDecorator(request, name);
        return result == null ? super.getDecorator(request, page) : result;
    }

    /**
     * 辅助方法，根据装饰器的名称，得到装饰器。
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