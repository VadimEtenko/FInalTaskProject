package project.web.command.requested;

import org.apache.log4j.Logger;
import project.db.NotificationDao;
import project.db.RoomDao;
import project.db.RequestedDao;
import project.db.UserDao;
import project.db.entity.Notification;
import project.db.entity.RequestedForBooking;
import project.db.entity.Room;
import project.db.entity.User;
import project.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VerifyRequestedCommand extends Command {

    private static final Logger log = Logger.getLogger(VerifyRequestedCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");
        List<RequestedForBooking> requestedRoomsList = new ArrayList<>();
        RequestedDao requestedDao = new RequestedDao();
        RoomDao roomDao = new RoomDao();
        NotificationDao notificationDao = new NotificationDao();
        UserDao userDao = new UserDao();
        User user = (User) request.getSession().getAttribute("user");


        for(String s : request.getParameterValues("requestedId")) {
            log.info("requestedId --> " + s);


            // get requested room number from jsp by id
            RequestedForBooking requestedForBooking =
                    requestedDao.findRequestedRoomById(Long.parseLong(s));

            log.info("Requested witch need to be verified --> " + requestedForBooking);

            // create new booked recording
            roomDao.createBookedRoom(requestedForBooking.getRoomId(),
                    requestedForBooking.getUserId());
            log.info("New booked room was created");


            User verifiedUser = userDao.findUsersByRequestedId(requestedForBooking.getId());

            for (User notVerifiedUsers : userDao.findUsersByRequestedRoomId(requestedForBooking.getRoomId())){
                if(!notVerifiedUsers.getId().equals(verifiedUser.getId())) {
                    log.trace("Found id DB: user witch requested was canceled:" + notVerifiedUsers);
                    notificationDao.createNotification(notVerifiedUsers.getId(),
                            Notification.getMessageCanceled(notVerifiedUsers,
                                    requestedDao.findRequestedRoomById(Long.parseLong(s))));
                    log.info("Send to user with id " + notVerifiedUsers.getId() + " notification!");
                    log.trace("Notification for user " + notVerifiedUsers.getId() + " was created in DB!");
                }

            }

            log.trace("Found id DB: user witch requested was verified:" + verifiedUser);
            notificationDao.createNotification(verifiedUser.getId(),
                    Notification.getMessageVerified(verifiedUser,
                            requestedDao.findRequestedRoomById(Long.parseLong(s))));

            // delete requested recording from table
            requestedDao.deleteRequestedByRoomNumber(requestedForBooking.getRoomNumber());

            log.info("Old requested with roomId " + s + " was deleted");
        }

        log.debug("Command finished");
        return new ListRequestedCommand().execute(request,response);
    }
}
