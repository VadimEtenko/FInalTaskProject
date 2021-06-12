package project.web.command;

import org.apache.log4j.Logger;
import project.Path;
import project.db.UserDao;
import project.db.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegistrationCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");
        UserDao userDao = new UserDao();
        String errorMessage = null;

        String name = request.getParameter("name");
        log.trace("Request parameter: name --> " + name);

        String surname = request.getParameter("surname");
        log.trace("Request parameter: surname --> " + surname);

        String login = request.getParameter("login");
        log.trace("Request parameter: login --> " + login);

        String password = request.getParameter("password");
        log.trace("Request parameter: password --> " + password);

        String email = request.getParameter("email");
        log.trace("Request parameter: email --> " + email);

        String local = request.getParameter("local");
        log.trace("Request parameter: local --> " + local);

        User user = new User(name, surname, login, password, email, 0, local);
        System.out.println(user);


        if (userDao.findUserByLogin(user.getLogin()) != null) {
            errorMessage = "This login already in used";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return Path.PAGE__ERROR_PAGE;
        }else {
            userDao.createNewUser(user);
            log.trace("Insert in DB: new User --> " + user);
        }
        log.debug("Command finished");
        return Path.PAGE__LOGIN;
    }
}
