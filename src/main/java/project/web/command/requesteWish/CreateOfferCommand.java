package project.web.command.requesteWish;

import org.apache.log4j.Logger;
import project.db.OffersDao;
import project.db.RequestWishDao;
import project.db.entity.BookingRooms;
import project.web.Path;
import project.web.command.Command;
import project.web.command.requestByRoom.CreateRequestCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;

public class CreateOfferCommand extends Command {

    private static final Logger log = Logger.getLogger(CreateOfferCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command started");

        OffersDao offersDao = new OffersDao();
        long userId = Long.parseLong(request.getParameter("userId"));
        log.trace("Get request parameter userId: " + userId);

        LocalDate time_in = null;
        LocalDate time_out = null;
        try {
            time_in = new Date(BookingRooms.sdf.parse(request.getParameter("time_in")).getTime()).toLocalDate();
            log.trace("Get request parameter time_in: " + time_in);
            time_out = new Date(BookingRooms.sdf.parse(request.getParameter("time_out")).getTime()).toLocalDate();
            log.trace("Get request parameter time_out: " + time_out);
        } catch (ParseException e) {
            String errorMessage = "Oops.. Smth get wrong";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
        }

        for (String suitableRoomIdString : request.getParameterValues("suitableRoomId")) {
            long roomId = Long.parseLong(suitableRoomIdString);
            log.trace("Get request parameter roomId: " + roomId);
            offersDao.createOffer(userId, roomId, time_in, time_out);
            log.trace("Create in database offer for user");
        }

        log.debug("Command finished");
        return Path.COMMAND__LIST_REQUEST_WISH;
    }
}
