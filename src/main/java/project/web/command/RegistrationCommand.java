package project.web.command;

import org.apache.log4j.Logger;
import project.web.Path;
import project.db.UserDao;
import project.db.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;


public class RegistrationCommand extends Command {

    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");
        UserDao userDao = new UserDao();
        String errorMessage = null;

        String name = new String(request.getParameter("name").
                getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        log.trace("Request parameter: name --> " + name);

        String surname = new String(request.getParameter("surname").
                getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        log.trace("Request parameter: surname --> " + surname);

        String login = request.getParameter("login");
        log.trace("Request parameter: login --> " + login);

        String password = request.getParameter("password");
        log.trace("Request parameter: password --> " + password);

        String secondPassword = request.getParameter("secondPassword");
        log.trace("Request parameter: secondPassword --> " + secondPassword);

        String email = request.getParameter("email");
        log.trace("Request parameter: email --> " + email);

        String local = request.getParameter("local");
        log.trace("Request parameter: local --> " + local);

        if (!password.equals(secondPassword)) {
            ResourceBundle rb = ResourceBundle.getBundle("notification", new Locale(local));
            log.info("First and second password aren't equals");

            String errorPassword = new String(rb.getString("registration.error.password").
                    getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

            request.setAttribute("errorPassword", errorPassword);
            log.trace("Set request parameter errorPassword --> " + errorPassword);
            return Path.PAGE__REGISTRATION;
        }

        User user = new User.Builder()
                .withName(name)
                .withSurname(surname)
                .withLogin(login)
                .withPassword(password)
                .withEmail(email)
                .withRoleId(0)
                .withLocale(local)
                .build();
        request.removeAttribute("errorPassword");

        if (userDao.findUserByLogin(user.getLogin()) != null) {
            errorMessage = "This login already in used";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return Path.PAGE__ERROR_PAGE;
        } else {
            userDao.createNewUser(user);
            log.trace("Insert in DB: new User --> " + user);
        }
        log.debug("Command finished");
        return Path.PAGE__LOGIN;
    }
}
