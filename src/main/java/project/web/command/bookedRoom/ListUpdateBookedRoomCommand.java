package project.web.command.bookedRoom;

import org.apache.log4j.Logger;
import project.web.Path;
import project.db.BookingDao;
import project.db.entity.BookingRooms;
import project.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListUpdateBookedRoomCommand extends Command {

    private static final Logger log = Logger.getLogger(ListBookedRoomCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");

        BookingDao bookingDao = new BookingDao();
        BookingRooms bookedRooms = bookingDao.findBookingRecordsById(
                Long.parseLong(request.getParameter("booked-id")));
        log.trace("Found in DB: bookedRooms --> " + bookedRooms);

        request.setAttribute("bookedRooms", bookedRooms);

        log.debug("Command finished");
        return Path.PAGE__UPDATE_BOOKED_ROOM ;
    }
}