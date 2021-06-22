package project.web.command.requesteWish;

import org.apache.log4j.Logger;
import project.web.Path;
import project.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PlanRequestCommand extends Command {

    private static final Logger log = Logger.getLogger(PlanRequestCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Forward to Path.PAGE__PLAN_REQUEST");
        return Path.PAGE__PLAN_REQUEST;
    }
}
