package project.web.command;

import org.apache.log4j.Logger;
import project.db.RoomDao;
import project.db.RequestedDao;
import project.db.bean.RequestedForBooking;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VerifyRequestedCommand extends Command {

    private static final Logger log = Logger.getLogger(VerifyRequestedCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");
        List<RequestedForBooking> requestedRoomsList = new ArrayList<>();
        RequestedDao requestedDao = new RequestedDao();
        RoomDao roomDao = new RoomDao();


        for(String s : request.getParameterValues("roomId")) {
            log.info("RoomId --> " + s);

            // get requested room number from jsp by id
            RequestedForBooking requestedForBooking =
                    requestedDao.findRequestedRoomsById(Long.parseLong(s));
            log.info("Requested witch need to be verified --> " + requestedForBooking);

            // create new booked recording
            roomDao.createBookedRoom(requestedForBooking.getRoomId(),
                    requestedForBooking.getUserId());
            log.info("New booked room was created");

            // delete requested recording from table
            requestedDao.deleteRequestedRoomById(Long.parseLong(s));
            log.info("Old requested with roomId " + s + " was deleted");
        }

        log.debug("Command finished");
        return new ListRequestedCommand().execute(request,response);
    }
}
