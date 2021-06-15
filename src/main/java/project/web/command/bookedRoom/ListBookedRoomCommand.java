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
import java.util.List;

public class ListBookedRoomCommand extends Command {

    private static final Logger log = Logger.getLogger(ListBookedRoomCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");

        List<BookingRooms> bookedRoomsList;

        BookingDao bookingDao = new BookingDao();
        bookedRoomsList = bookingDao.findAllBookingRecords();
        log.trace("Found in DB: bookedRoomsList --> " + bookedRoomsList);

        bookedRoomsList.sort((r1, r2) -> (int) (r1.getRoomNumber() - r2.getRoomNumber()));

        request.setAttribute("bookedRoomsList", bookedRoomsList);
        log.debug("Command finished");
        return Path.PAGE__BOOKED_ROOM_LIST;
    }
}
