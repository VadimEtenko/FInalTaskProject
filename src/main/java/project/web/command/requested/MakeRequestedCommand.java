package project.web.command.requested;

import org.apache.log4j.Logger;
import project.db.RequestedDao;
import project.db.RoomDao;
import project.db.entity.User;
import project.web.command.Command;
import project.web.command.ListFreeRoomsCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MakeRequestedCommand extends Command {

    private static final Logger log = Logger.getLogger(MakeRequestedCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        log.debug("Command starts");
        RequestedDao requestedDao = new RequestedDao();
        RoomDao roomDao = new RoomDao();

        HttpSession session = request.getSession();

        long userId = ((User) session.getAttribute("user")).getId();
        log.info("Command was called by user id --> " + userId);

        StringBuilder createdSuccessfulMessageLabel =
                new StringBuilder("Request for room(s) number ");

        StringBuilder createdUnsuccessfulMessageLabel =
                new StringBuilder("Request for room(s) number ");
        for (String s : request.getParameterValues("roomId")) {

            log.debug("Request room number --> " + s);
            long roomId = Long.parseLong(s);
            int roomNumber = roomDao.findBookedRoomNumberById(roomId);
            log.debug("Find room with id + " + roomId + " --> " + s);

            if (requestedDao.isCreatedRequestRoomByUserIdAndRoomNumber(userId, roomId)) {
                requestedDao.createRequest(userId, roomId);
                createdSuccessfulMessageLabel.append(roomNumber).append(" ");
                log.info("Request for room " + roomNumber + " created");
            } else {
                createdUnsuccessfulMessageLabel.append(roomNumber).append(" ");
                log.info("Request for room" + roomNumber +
                        " wasn't created, it's already exist");
            }
        }

        createdSuccessfulMessageLabel.append(" was successfully created");
        createdUnsuccessfulMessageLabel.append(" wasn't created, it's already exist");

        request.setAttribute("createdSuccessfulMessageLabel",
                createdSuccessfulMessageLabel.toString());

        request.setAttribute("createdUnsuccessfulMessageLabel",
                createdUnsuccessfulMessageLabel.toString());

        log.debug("Command finished");
        return new ListFreeRoomsCommand().execute(request, response);
    }
}
