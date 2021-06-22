package project.web.command.requestByRoom;

import org.apache.log4j.Logger;
import project.db.*;
import project.db.entity.Notification;
import project.db.entity.RequestedForBooking;
import project.db.entity.User;
import project.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VerifyRequestedCommand extends Command {

    private static final Logger log = Logger.getLogger(VerifyRequestedCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");

        RequestDao requestDao = new RequestDao();
        BookingDao bookingDao = new BookingDao();
        NotificationDao notificationDao = new NotificationDao();
        UserDao userDao = new UserDao();

        String requestedId = request.getParameter("requestedId");
        log.info("requestedId --> " + requestedId);

        // get requested room number from jsp by id
        RequestedForBooking requestedForBooking =
                requestDao.findRequestedRoomById(Long.parseLong(requestedId));
        log.info("Requested witch need to be verified --> " + requestedForBooking);

        // create new booked recording
        bookingDao.createBookedRoom(requestedForBooking.getRoomId(),
                requestedForBooking.getUserId(),
                requestedForBooking.getTimeIn(),
                requestedForBooking.getTimeOut());
        log.info("New booked room was created");

        User verifiedUser = userDao.findUserByRequestedId(requestedForBooking.getId());
        log.info("Found user who create a request: " + verifiedUser);

        //Search for all users, who want to booked this room, and didn't get it
        for (User notVerifiedUsers : userDao.findUsersByRequestedRoomId(requestedForBooking.getRoomId())) {
            if (!notVerifiedUsers.getId().equals(verifiedUser.getId())) {
                log.trace("Found id DB: user witch requested was canceled:" + notVerifiedUsers);


                //crete notification by user id, booked record which was canceled and text
                notificationDao.createNotification(notVerifiedUsers.getId(),
                        Notification.getMessageCanceled(notVerifiedUsers,
                                requestDao.findRequestedRoomById(Long.parseLong(requestedId)).getRoomNumber()));

                log.info("Send to user with id " + notVerifiedUsers.getId() + " notification!");
                log.trace("Notification for user " + notVerifiedUsers.getId() + " was created in DB!");
            }

        }

        log.trace("Found id DB: user witch requested was verified:" + verifiedUser);
        notificationDao.createNotification(verifiedUser.getId(),
                bookingDao.findBookingRecordByUserIdAndRoomId(
                        verifiedUser.getId(),
                        requestedForBooking.getRoomId()).getId(),
                Notification.getMessageVerified(verifiedUser,
                        requestDao.findRequestedRoomById(Long.parseLong(requestedId)).getRoomNumber()));

        // delete requested recording from table
        requestDao.deleteRequestedByRoomNumber(requestedForBooking.getRoomNumber());
        log.info("Old requested with roomId " + requestedId + " was deleted");


        log.debug("Command finished");
        return new ListRequestedCommand().execute(request, response);
    }
}
