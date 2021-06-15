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
        long statusId = Long.parseLong(request.getParameter("status-id"));

        log.info("Request parameter: bookedId --> " + bookedId);
        log.info("Request parameter: statusId --> " + statusId);

        BookingDao bookingDao = new BookingDao();
        bookingDao.editBookingRecords(bookedId, statusId);
        log.info("Record was updated");

        bookingDao.deleteFreeReservation();
        log.info("Free records was deleted");


        log.debug("Command finished, req");
        return Path.COMMAND__LIST_BOOKED_ROOMS;
    }
}

