package project.web.command.requesteWish;

import org.apache.log4j.Logger;
import project.db.*;
import project.db.entity.Notification;
import project.db.entity.Offer;
import project.db.entity.User;
import project.web.Path;
import project.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AgreeWithOffersCommand extends Command {

    private static final Logger log = Logger.getLogger(AgreeWithOffersCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command started");

        BookingDao bookingDao = new BookingDao();
        OffersDao offersDao = new OffersDao();
        NotificationDao notificationDao = new NotificationDao();

        User user = (User)request.getSession().getAttribute("user");
        log.trace("Get session attribute user: " + user);

        new RequestWishDao().deleteRequestWishByIdUserId(user.getId());
        log.trace("Delete from database desire of user with id:" + user.getId());

        long roomId = Long.parseLong(request.getParameter("offeredRoomId"));
        log.trace("Get request parameter offeredRoomId: " + roomId);

        //we take only 1 entry, since all dates are the same for offers
        Offer offer = offersDao.findOffersByUserId(user.getId()).get(0);
        log.trace("Find in database rooms offered to this user: " + offer);

        offersDao.deleteOffersByUserId(user.getId());
        log.trace("Removed all offers to user from database");

        bookingDao.createBookedRoom(roomId,user.getId(), offer.getTime_in(), offer.getTime_out());
        log.trace("Created new booked room");

        notificationDao.createNotification(user.getId(),
                bookingDao.findBookingRecordByUserIdAndRoomId(user.getId(),roomId).getId(),
                Notification.getMessageVerified(user,
                        new RoomDao().findRoomById(roomId).getNumber()));
        log.trace("Created new notification for this user");

        log.debug("Command finished");
        return Path.PAGE__FIND_FREE_ROOM_LIST;
    }
}
