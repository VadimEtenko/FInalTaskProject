package project.web.command;

import project.web.Path;
import project.db.UserDao;
import project.db.entity.Role;
import project.db.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;


/**
 * Login command.
 */
public class LoginCommand extends Command {

    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");

        HttpSession session = request.getSession();

        // obtain login and password from the request
        String login = request.getParameter("login");
        log.trace("Request parameter: login --> " + login);

        String password = request.getParameter("password");
        log.trace("Request parameter: password --> " + password);

        String errorMessage = null;
        String forward = Path.PAGE__ERROR_PAGE;

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;
        }

        User user = new UserDao().findUserByLogin(login);;

        //First find user by login, then by email
        if(user == null){
            log.trace("Didn't find in DB user by login, try to find by email");
            user = new UserDao().findUserByEmail(login);
        }

        if (user == null || !password.equals(user.getPassword())) {
            errorMessage = "Cannot find user with such login/email/password";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;
        } else {
            log.trace("Found in DB: user --> " + user);
            Role userRole = Role.getRole(user);
            log.trace("userRole --> " + userRole);

            if (userRole == Role.CLIENT)
                forward = Path.PAGE__FIND_FREE_ROOM_LIST;

            if (userRole == Role.MANAGER)
                forward = Path.COMMAND__LIST_REQUESTED;

            session.setAttribute("user", user);
            log.trace("Set the session attribute: user --> " + user);

            session.setAttribute("userRole", userRole);
            log.trace("Set the session attribute: userRole --> " + userRole);
            log.info("User " + user + " logged as " + userRole.toString().toLowerCase());


            String userLocal = user.getLocale();
            log.trace("userLocal --> " + userLocal);

            if (userLocal != null && !userLocal.isEmpty()) {
                Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLocal);

                session.setAttribute("defaultLocale", userLocal);
                log.trace("Set the session attribute: defaultLocale --> " + userLocal);

                log.info("Locale for user: defaultLocale --> " + userLocal);
            }
        }

        log.debug("Command finished");
        return forward;
    }

}