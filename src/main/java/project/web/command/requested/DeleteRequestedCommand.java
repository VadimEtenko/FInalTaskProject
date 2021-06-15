package project.web.command.requested;

import org.apache.log4j.Logger;
import project.web.Path;
import project.db.RequestedDao;
import project.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteRequestedCommand extends Command {

    private static final Logger log = Logger.getLogger(DeleteRequestedCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");

        // obtain booked-id and status-id from jsp
        for (String s : request.getParameterValues("bookedId")) {
                long bookedId = Long.parseLong(s);
                log.info("Request parameter: bookedId --> " + bookedId);

                new RequestedDao().deleteRequestById(bookedId);
                log.info("Record was deleted");
            }

        log.debug("Command finished");
        return Path.COMMAND__LIST_FREE_ROOMS;
    }
}
