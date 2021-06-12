package project.web.tags;

import java.util.Calendar;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class CustomTagClass extends TagSupport{

    public int doStartTag() throws JspException {
        JspWriter out=pageContext.getOut();//returns the instance of JspWriter
        try{
            out.print("VIP Hotel, by Vadim Etenko, 2021");
        }catch(Exception e){System.out.println(e);}
        return SKIP_BODY;
    }
}