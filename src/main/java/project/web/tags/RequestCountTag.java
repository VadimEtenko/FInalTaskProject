package project.web.tags;

import project.db.RequestDao;
import project.db.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class RequestCountTag extends TagSupport {

    protected User user;

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            int count = new RequestDao().findAllRequestedRooms().size();
            if (count > 0)
                out.print(" (" + count + ")");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
