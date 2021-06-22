package project.web.tags;

import project.db.OffersDao;
import project.db.entity.User;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class OffersCountTag extends TagSupport {

    protected User user;

    @Override
    public int doStartTag() {
        JspWriter out = pageContext.getOut();
        try {
            int count = new OffersDao().findOffersByUserId(user.getId()).size();
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

