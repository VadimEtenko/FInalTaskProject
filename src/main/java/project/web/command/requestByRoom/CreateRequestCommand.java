package project.web.command.requestByRoom;

import org.apache.log4j.Logger;
import project.db.RequestDao;
import project.db.RoomDao;
import project.db.entity.User;
import project.web.command.Command;
import project.web.command.ListFreeRoomsCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CreateRequestCommand extends Command {

    private static final Logger log = Logger.getLogger(CreateRequestCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        log.debug("Command starts");
        RequestDao requestDao = new RequestDao();
        RoomDao roomDao = new RoomDao();

        HttpSession session = request.getSession();

        long userId = ((User) session.getAttribute("user")).getId();
        log.info("Command was called by user id --> " + userId);

        StringBuilder createdSuccessfulMessageLabel =
                new StringBuilder("Request for room number ");

        StringBuilder createdUnsuccessfulMessageLabel =
                new StringBuilder("Request for room number ");
        long roomId =  Long.parseLong(request.getParameter("roomIdParam"));

        log.debug("Request room with id --> " + roomId);

        int roomNumber = roomDao.findRoomNumberById(roomId);
        log.debug("Find room with id + " + roomId + " --> " + roomId);



        if (requestDao.isCreatedRequestRoomByUserIdAndRoomNumber(userId, roomId)) {
            requestDao.createRequest(userId, roomId);
            createdSuccessfulMessageLabel.append(roomNumber).append(" ");
            log.info("Request for room " + roomNumber + " created");
        } else {
            createdUnsuccessfulMessageLabel.append(roomNumber).append(" ");
            log.info("Request for room" + roomNumber +
                    " wasn't created, it'roomIdParam already exist");
        }


        createdSuccessfulMessageLabel.append(" was successfully created");
        createdUnsuccessfulMessageLabel.append(" wasn't created, it'roomIdParam already exist");

        request.setAttribute("createdSuccessfulMessageLabel",
                createdSuccessfulMessageLabel.toString());

        request.setAttribute("createdUnsuccessfulMessageLabel",
                createdUnsuccessfulMessageLabel.toString());

        log.debug("Command finished");
        return new ListFreeRoomsCommand().execute(request, response);
    }
}
