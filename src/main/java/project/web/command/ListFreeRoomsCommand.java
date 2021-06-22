package project.web.command;

import org.apache.log4j.Logger;
import project.db.entity.BookingRooms;
import project.web.Path;
import project.db.RoomDao;
import project.db.entity.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class ListFreeRoomsCommand extends Command {

    private static final Logger log = Logger.getLogger(ListFreeRoomsCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");

        LocalDate time_in = null;
        LocalDate time_out = null;
        try {
            time_in = new Date(BookingRooms.sdf.parse(request.getParameter("time_in")).getTime()).toLocalDate();
            log.trace("Get request parameter time_in " + time_in);

            time_out = new Date(BookingRooms.sdf.parse(request.getParameter("time_out")).getTime()).toLocalDate();
            log.trace("Get request parameter time_out " + time_out);
        } catch (ParseException e) {
            e.printStackTrace();
            String errorMessage = "The entered date is incorrect";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return Path.PAGE__ERROR_PAGE;
        }

        //check, is the arrival date greater than the departure date
        if (time_in.isAfter(time_out)) {
            String errorMessage = "The entered date is incorrect";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return Path.PAGE__ERROR_PAGE;
        }


        // get free room list list
        List<Room> freeRoomsList = new RoomDao().findFreeRooms(time_in, time_out);
        log.trace("Found in DB: freeRoomsList --> " + freeRoomsList);


        String filterType;

        if (request.getParameter("type-filter") != null)
            filterType = request.getParameter("type-filter");
        else
            filterType = "cost";
        log.trace("Get request parameter type-filter --> " + filterType);

        switch (filterType) {
            case "cost":
                freeRoomsList.sort((r1, r2) -> (int) (r1.getCost() - r2.getCost()));
                break;
            case "beds":
                freeRoomsList.sort(Comparator.comparingInt(Room::getNumberOfBeds));
                break;
            case "class":
                freeRoomsList.sort(Comparator.comparing(Room::getRoomClass));
                break;
            default:
                freeRoomsList.sort((r1, r2) -> (int) (r1.getId() - r2.getId()));
        }
        log.trace("List was sorted by got type-filter: " + filterType);

        // put free rooms list to the request
        request.setAttribute("freeRoomsList", freeRoomsList);
        log.trace("Set the request attribute: freeRoomsList --> " + freeRoomsList);

        request.setAttribute("time_in", time_in);
        log.trace("Set the request attribute: time_in --> " + time_in);

        request.setAttribute("time_out", time_out);
        log.trace("Set the request attribute: time_out --> " + time_out);

        log.debug("Command finished");
        return Path.PAGE__FREE_ROOM_LIST;
    }

}