package project.web.command;

import org.apache.log4j.Logger;
import project.web.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FindFreeRoomsCommand extends Command {

    private static final Logger log = Logger.getLogger(FindFreeRoomsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Go to " + Path.PAGE__FIND_FREE_ROOM_LIST);
        return Path.PAGE__FIND_FREE_ROOM_LIST;
    }

}
