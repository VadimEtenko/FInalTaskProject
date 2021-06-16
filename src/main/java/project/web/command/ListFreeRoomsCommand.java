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
import java.time.LocalDateTime;
import java.util.List;

public class ListFreeRoomsCommand extends Command {

    private static final long serialVersionUID = 7732286214029478505L;

    private static final Logger log = Logger.getLogger(ListFreeRoomsCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command starts");
        Date time_in = null;
        Date time_out = null;
        try {
            time_in = new Date(BookingRooms.sdf.parse(request.getParameter("time_in")).getTime());
            time_out = new Date(BookingRooms.sdf.parse(request.getParameter("time_out")).getTime());
        }catch(ParseException e){
            e.printStackTrace();
        }
        // get free room list list
        List<Room> freeRoomsList = new RoomDao().findFreeRooms(time_in,time_out);
        log.trace("Found in DB: freeRoomsList --> " + freeRoomsList);

        // sort menu by number (lambda)
        freeRoomsList.sort((r1, r2) -> (int) (r1.getNumber() - r2.getNumber()));

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