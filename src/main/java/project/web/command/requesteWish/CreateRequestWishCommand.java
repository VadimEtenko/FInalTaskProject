package project.web.command.requesteWish;

import org.apache.log4j.Logger;
import project.db.RequestWishDao;
import project.db.entity.BookingRooms;
import project.db.entity.ClassOfRooms;
import project.db.entity.RequestWish;
import project.db.entity.User;
import project.web.Path;
import project.web.command.Command;
import project.web.command.ListFreeRoomsCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateRequestWishCommand extends Command {

    private static final Logger log = Logger.getLogger(CreateRequestWishCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command started");
        RequestWishDao requestWishDao = new RequestWishDao();

        LocalDate time_in;
        LocalDate time_out;
        try {
            time_in = new Date(BookingRooms.sdf.parse(request.getParameter("time_in")).getTime()).toLocalDate();
            log.trace("Get request parameter time_in: " + time_in);
            time_out = new Date(BookingRooms.sdf.parse(request.getParameter("time_out")).getTime()).toLocalDate();
            log.trace("Get request parameter time_out: " + time_out);
            if (time_in.isAfter(time_out)) {
                String errorMessage = "The entered date is incorrect";
                request.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                return Path.PAGE__ERROR_PAGE; // yes, return
            }
        } catch (ParseException e) {
            String errorMessage = "Oops.. Smth get wrong";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return Path.PAGE__ERROR_PAGE;
        }

        long classId = Long.parseLong(request.getParameter("classId"));
        int numberOfBeds = Integer.parseInt(request.getParameter("numberOfBeds"));
        long userId = ((User) request.getSession().getAttribute("user")).getId();

        requestWishDao.createRequestWish(userId, classId, numberOfBeds, time_in, time_out);
        log.trace("Request by wish was created: " + userId + " " +
                classId + " " + numberOfBeds + " " + time_in + " " + time_out);

        log.debug("Command finished");
        return Path.PAGE__FIND_FREE_ROOM_LIST;
    }
}
