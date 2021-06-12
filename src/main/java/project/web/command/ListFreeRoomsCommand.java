package project.web.command;

import org.apache.log4j.Logger;
import project.Path;
import project.db.RoomDao;
import project.db.entity.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListFreeRoomsCommand extends Command {

    private static final long serialVersionUID = 7732286214029478505L;

    private static final Logger log = Logger.getLogger(ListFreeRoomsCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command starts");

        // get free room list list
        List<Room> freeRoomsList = new RoomDao().findFreeRooms();
        log.trace("Found in DB: freeRoomsList --> " + freeRoomsList);

        // sort menu by number (lambda)
        freeRoomsList.sort((r1, r2) -> (int) (r1.getNumber() - r2.getNumber()));

        // put free rooms list to the request
        request.setAttribute("freeRoomsList", freeRoomsList);
        log.trace("Set the request attribute: freeRoomsList --> " + freeRoomsList);

        log.debug("Command finished");
        return Path.PAGE__FREE_ROOM_LIST;
    }

}