package project.web.command;

import org.apache.log4j.Logger;
import project.db.RequestedDao;
import project.db.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MakeRequestedCommand extends Command{

    private static final Logger log = Logger.getLogger(MakeRequestedCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        log.debug("Command starts");
        RequestedDao requestedDao = new RequestedDao();
        HttpSession session = request.getSession();

        long userId = ((User)session.getAttribute("user")).getId();
        log.info("Command was called by user id --> " + userId);


        for(String s : request.getParameterValues("roomId")) {
            log.debug("Request room number --> " + s);
            long roomId = Long.parseLong(s);
            requestedDao.createRequest(userId, roomId);
            log.info("Request was created");
        }

        log.debug("Command finished");
        return new ListFreeRoomsCommand().execute(request,response);
    }
}
