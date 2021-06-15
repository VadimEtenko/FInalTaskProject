package project.web.command.requested;

import org.apache.log4j.Logger;
import project.web.Path;
import project.db.RequestedDao;
import project.db.entity.RequestedForBooking;
import project.web.command.Command;
import project.web.command.ListFreeRoomsCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListRequestedCommand extends Command {

    private static final Logger log = Logger.getLogger(ListFreeRoomsCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command starts");

        List<RequestedForBooking> requestedRoomsList =
                new RequestedDao().findAllRequestedRooms();
        log.trace("Found in DB: requested rooms List --> " + requestedRoomsList);

        // sort menu by number (lambda)
        requestedRoomsList.sort((a1, a2) -> (int) (a1.getId() - a2.getId()));

        // put free rooms list to the request
        request.setAttribute("requestedRoomsList", requestedRoomsList);
        log.trace("Set the request attribute: requestedRoomsList --> " + requestedRoomsList);

        log.debug("Command finished");
        return Path.PAGE__LIST_REQUESTED;
    }

}