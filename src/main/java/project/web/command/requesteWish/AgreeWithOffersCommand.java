package project.web.command.requesteWish;

import project.db.*;
import project.db.entity.Notification;
import project.db.entity.RequestWish;
import project.db.entity.RequestedForBooking;
import project.db.entity.User;
import project.web.Path;
import project.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AgreeWithOffersCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        BookingDao bookingDao = new BookingDao();
        RequestWishDao requestWishDao = new RequestWishDao();

        User user = (User)request.getSession().getAttribute("user");
        NotificationDao notificationDao = new NotificationDao();

        new RequestWishDao().deleteRequestWishByIdUserId(user.getId());
        new OffersDao().deleteOffersByUserId(user.getId());

        long userId = Long.parseLong(request.getParameter("userId"));
        long roomId = Long.parseLong(request.getParameter("offeredRoomId"));

        bookingDao.createBookedRoom(userId, roomId);

        notificationDao.createNotification(userId,
                bookingDao.findBookingRecordsByUserIdAndRoomId
                        (user.getId(),roomId).getId(),
                Notification.getMessageVerified(user,
                        new RoomDao().findRoomNumberById(roomId)));




        return Path.PAGE__FIND_FREE_ROOM_LIST;
    }
}
