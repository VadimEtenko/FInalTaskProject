package project.web.command.requesteWish;

import org.apache.log4j.Logger;
import project.db.RequestWishDao;
import project.db.entity.RequestWish;
import project.web.Path;
import project.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListRequestWishCommand extends Command {

    private static final Logger log = Logger.getLogger(ListRequestWishCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command start");

        List<RequestWish> requestWishList= new RequestWishDao().findAllRequestWish();
        log.trace("Found in database all request by wishes: " + requestWishList);

        request.setAttribute("requestWishList", requestWishList);
        log.trace("Set request attribute requestWishList: " + requestWishList);

        log.debug("Command finished");
        return Path.PAGE__REQUEST_WISH_LIST;

    }
}
