package project.web.command.notification;

import org.apache.log4j.Logger;
import project.db.BookingDao;
import project.web.Path;
import project.db.NotificationDao;
import project.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteNotificationCommand extends Command {

    private static final Logger log = Logger.getLogger(DeleteNotificationCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");
        NotificationDao notificationDao = new NotificationDao();

        long notificationId = Long.parseLong(request.getParameter("notificationId"));
        log.trace("Request parameter: notificationId --> " + notificationId);

        long bookedId = Long.parseLong(request.getParameter("bookedId"));
        log.trace("Request parameter: bookedId --> " + bookedId);

        //First - pay, then - delete !!!
        new BookingDao().makePaidByNotificationId(notificationId);
        log.info("Booked was paid");

        notificationDao.deleteNotificationById(notificationId);
        log.trace("Notification was deleted");

        log.debug("Command finished");
        return Path.COMMAND__USER_NOTIFICATIONS_LIST;
    }

}

