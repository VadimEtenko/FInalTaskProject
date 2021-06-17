package project.web.command.bookedRoom;

import org.apache.log4j.Logger;
import project.web.Path;
import project.db.BookingDao;
import project.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateBookedRoomCommand extends Command {

    private static final Logger log = Logger.getLogger(ListBookedRoomCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");

        // obtain booked-id and status-id from jsp
        long bookedId = Long.parseLong(request.getParameter("booked-id"));
        log.trace("Request parameter: bookedId --> " + bookedId);

        long statusId = Long.parseLong(request.getParameter("status-id"));
        log.trace("Request parameter: statusId --> " + statusId);

        BookingDao bookingDao = new BookingDao();
        bookingDao.editBookingRecordsStatus(bookedId, statusId);
        log.trace("Booking record " + bookedId + " was updated to " + statusId + "status");

        bookingDao.deleteFreeReservation();
        log.trace("Free records was deleted");

        log.debug("Command finished, req");
        return Path.COMMAND__LIST_BOOKED_ROOMS;
    }
}

