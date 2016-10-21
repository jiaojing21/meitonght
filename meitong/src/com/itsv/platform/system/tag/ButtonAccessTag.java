package com.itsv.platform.system.tag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import com.itsv.gbp.core.admin.security.UserInfoAdapter;
import com.itsv.gbp.core.security.util.SecureTool;
/**
 * @author houxiaochen
 */
public class ButtonAccessTag extends TagSupport {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String name = null;
    private String onclick = null;
    private String style = null;
    private String value = null;
    private String code = null;
    private String type = null;
    public void setName(String name) {
        this.name = name;
    }

    public int doStartTag() throws JspException {
        UserInfoAdapter adapter = (UserInfoAdapter) SecureTool.getCurrentUser();
        com.itsv.gbp.core.admin.vo.SessionUser su =adapter.getRealUser();
        List<String> fcs = su.getFunctions();
        
        try {
            JspWriter out = pageContext.getOut();
            StringBuffer sb = new StringBuffer("");
            
            if(fcs.contains(code)){
                sb.append("<input name=\""+name+"\" type=\""+type+"\" onclick=\""+onclick+"\"");
                sb.append("class=\""+style+"\" value=\""+value+"\">");
                out.print(sb.toString());
            }
            //out.print("Hello! " + name);
        } catch (Exception e) {
            throw new JspException(e);
        }
        return EVAL_PAGE;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

}
