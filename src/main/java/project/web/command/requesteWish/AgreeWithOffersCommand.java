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
        BookingDao bookingDao = new BookingDao();
        OffersDao offersDao = new OffersDao();
        User user = (User)request.getSession().getAttribute("user");
        NotificationDao notificationDao = new NotificationDao();

        new RequestWishDao().deleteRequestWishByIdUserId(user.getId());
        long roomId = Long.parseLong(request.getParameter("offeredRoomId"));

        Offer offer = offersDao.findOffersByUserId(user.getId()).get(0);
        offersDao.deleteOffersByUserId(user.getId());

        bookingDao.createBookedRoom(roomId,user.getId(), offer.getTime_in(), offer.getTime_out());

        notificationDao.createNotification(user.getId(),
                bookingDao.findBookingRecordByUserIdAndRoomId(user.getId(),roomId).getId(),
                Notification.getMessageVerified(user,
                        new RoomDao().findRoomById(roomId).getNumber()));


        return Path.PAGE__FIND_FREE_ROOM_LIST;
    }
}
