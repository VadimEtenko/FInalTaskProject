package project.web.command.requestByRoom;

import org.apache.log4j.Logger;
import project.db.RequestDao;
import project.db.RoomDao;
import project.db.entity.BookingRooms;
import project.db.entity.User;
import project.web.Path;
import project.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;


public class CreateRequestCommand extends Command {

    private static final Logger log = Logger.getLogger(CreateRequestCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        log.debug("Command starts");

        RequestDao requestDao = new RequestDao();
        RoomDao roomDao = new RoomDao();


        String forward = Path.PAGE__FIND_FREE_ROOM_LIST;
        log.info("Set first forward:" + Path.PAGE__FIND_FREE_ROOM_LIST);

        long userId = ((User) request.getSession().getAttribute("user")).getId();
        log.trace("Get session attribute's parameter --> " + userId);
        log.info("Command was called by user id --> " + userId);

        //check is user
        if (request.getParameterValues("roomId") == null) {
            String errorMessage = "You must choose at list one room";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return Path.PAGE__ERROR_PAGE;
        }


        for (String roomIsString : request.getParameterValues("roomId")) {
            long roomId = Long.parseLong(roomIsString);
            log.trace("Get parameter roomIdParam --> " + roomId);

            int roomNumber = roomDao.findRoomById(roomId).getNumber();
            log.trace("Found in DB: room number with id + " + roomId + " --> " + roomNumber);

            LocalDate time_in = null;
            LocalDate time_out = null;
            try {
                time_in = new Date(BookingRooms.sdf.parse(request.getParameter("time_in")).getTime()).toLocalDate();
                log.trace("Get request parameter time_in: " + time_in);
                time_out = new Date(BookingRooms.sdf.parse(request.getParameter("time_out")).getTime()).toLocalDate();
                log.trace("Get request parameter time_out: " + time_out);
                if (time_in.isAfter(time_out)) {
                    String errorMessage = "The entered date is incorrect";
                    request.setAttribute("errorMessage", errorMessage);
                    log.error("errorMessage --> " + errorMessage);
                    return Path.PAGE__ERROR_PAGE; // yes, return
                }
            } catch (ParseException e) {
                String errorMessage = "Oops.. Smth get wrong";
                request.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                return Path.PAGE__ERROR_PAGE;
            }


            //check is user already creat request for this room
            if (requestDao.isCreatedRequestRoomByUserIdAndRoomNumber(userId, roomId)) {
                requestDao.createRequest(userId, roomId, time_in, time_out);
                log.info("Request for room " + roomNumber + " created");
            } else {
                String errorMessage = "Request for room" + roomNumber +
                        " wasn't created, it's already exist";
                request.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                forward = Path.PAGE__ERROR_PAGE;
            }
        }
        log.debug("Command finished");
        return forward;
    }
}
