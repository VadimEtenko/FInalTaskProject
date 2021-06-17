package project.web.command.notification;

import org.apache.log4j.Logger;
import project.web.Path;
import project.db.NotificationDao;
import project.db.entity.Notification;
import project.db.entity.User;
import project.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListNotificationCommand extends Command {

    private static final long serialVersionUID = 7732286214029478505L;

    private static final Logger log = Logger.getLogger(ListNotificationCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");

        User user = (User) request.getSession().getAttribute("user");
        log.trace("Get session attribute user: " + user);

        // get user's notification list
        List<Notification> notificationList =
                new NotificationDao().findNotificationsByUserId(user.getId());
        log.trace("Found in DB: notificationList for user " + user.getId() + " --> " + notificationList);

        // sort notifications by number (lambda)
        notificationList.sort((r1, r2) -> (int) (r1.getId() - r2.getId()));
        log.trace("List was sorted by notifications id");

        // put notification list to the request
        request.setAttribute("notificationList", notificationList);
        log.trace("Set the request attribute: notificationList --> " + notificationList);

        log.debug("Command finished");
        return Path.PAGE__USER_NOTIFICATIONS_LIST;
    }
}
