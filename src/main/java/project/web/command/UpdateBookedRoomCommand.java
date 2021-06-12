package project.web.command;

import org.apache.log4j.Logger;
import project.Path;
import project.db.BookingDao;
import project.db.entity.BookingRooms;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UpdateBookedRoomCommand extends Command{

    private static final Logger log = Logger.getLogger(ListBookedRoomCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");

        // obtain login and password from the request
        long bookedId = Long.parseLong(request.getParameter("booked-id"));
        long statusId = Long.parseLong(request.getParameter("status"));

        log.info("Request parameter: bookedId --> " + bookedId);
        log.info("Request parameter: statusId --> " + statusId);

        new BookingDao().editBookingRecords(bookedId, statusId);

        log.debug("Command finished, req");
        return Path.PAGE__BOOKED_ROOM_LIST;
    }
}

