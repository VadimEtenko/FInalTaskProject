package project.web.command;

import org.apache.log4j.Logger;
import project.db.UserDao;
import project.db.entity.Role;
import project.db.entity.User;
import project.web.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LocalChangeCommand extends Command{

    private static final Logger log = Logger.getLogger(LocalChangeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");
        HttpSession session = request.getSession();
        UserDao userDao = new UserDao();

        User newUser = (User) session.getAttribute("user");
        log.trace("Get Attribute user " + newUser);

        newUser.setLocale(request.getParameter("newLocal"));

        log.trace("Set Attribute newLocale" + newUser.getLocale());

        userDao.updateUser(newUser);
        log.trace("Update user");

        session.setAttribute("user",newUser);
        log.trace("Set new session attribute user" + newUser);

        session.setAttribute("defaultLocale", newUser.getLocale());
        log.trace("Set new session attribute defaultLocale" + newUser.getLocale());

        Role UserRole = Role.getRole(newUser);

        if (UserRole == Role.CLIENT)
            return Path.COMMAND__LIST_FREE_ROOMS;

        if (UserRole == Role.MANAGER)
            return Path.COMMAND__LIST_REQUESTED;

        log.debug("Command finished");
        return Path.PAGE__ERROR_PAGE;
    }
}
