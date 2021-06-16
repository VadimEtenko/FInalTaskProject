package project.web.command.requesteWish;

import project.db.*;
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


        User user = (User)request.getSession().getAttribute("user");
        NotificationDao notificationDao = new NotificationDao();


        new RequestWishDao().deleteRequestWishByIdUserId(user.getId());
        new OffersDao().deleteOffersByUserId(user.getId());


        long userId = Long.parseLong(request.getParameter("userId"));
        long roomId = Long.parseLong(request.getParameter("offeredRoomId"));

        bookingDao.createBookedRoom(userId, roomId);

        return Path.COMMAND__LIST_FREE_ROOMS;


    }
}
