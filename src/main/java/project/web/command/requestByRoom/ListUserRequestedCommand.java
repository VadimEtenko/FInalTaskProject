package project.web.command.requestByRoom;

import org.apache.log4j.Logger;
import project.web.Path;
import project.db.RequestDao;
import project.db.entity.RequestedForBooking;
import project.db.entity.User;
import project.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ListUserRequestedCommand extends Command {
    private static final Logger log = Logger.getLogger(ListUserRequestedCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        log.debug("Command starts");
        RequestDao requestDao = new RequestDao();
        HttpSession session = request.getSession();

        log.debug("Get user id from session");
        long userId = ((User)session.getAttribute("user")).getId();

        List<RequestedForBooking> userRequestedRoomsList =
                new RequestDao().findRequestedRoomsByUserId(userId);

        log.trace("Found in DB: user's requested rooms List --> " + userRequestedRoomsList);

        // sort menu by number (lambda)
        userRequestedRoomsList.sort((a1, a2) -> (int) (a1.getRoomNumber() - a2.getRoomNumber()));

        // put free rooms list to the request
        request.setAttribute("userRequestedRoomsList", userRequestedRoomsList);
        log.trace("Set the request attribute: requestedRoomsList --> " + userRequestedRoomsList);

        log.debug("Command finished");
        return Path.PAGE__USER_REQUESTED_LIST;
    }
}
