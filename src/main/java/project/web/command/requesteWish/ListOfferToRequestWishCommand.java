package project.web.command.requesteWish;

import org.apache.log4j.Logger;
import project.db.RequestWishDao;
import project.db.RoomDao;
import project.db.entity.RequestWish;
import project.db.entity.Room;
import project.web.Path;
import project.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListOfferToRequestWishCommand extends Command {

    private static final Logger log = Logger.getLogger(ListOfferToRequestWishCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command started");

        long wishId = Long.parseLong(request.getParameter("wishId"));
        log.trace("Get request parameter wishId: " + wishId);

        RequestWish rw = new RequestWishDao().findRequestWishById(wishId);
        log.trace("Found in database RequestWish by id: " + rw);

        request.setAttribute("wish",rw);
        log.trace("Set request attribute wish: " + rw);

        List<Room> suitableRooms = new RoomDao().findAllFreeRoomsByCriteria(rw.getClass_id(),rw.getNumber_of_beds());
        log.trace("Found in database all free rooms by user wishes: " + suitableRooms);

        request.setAttribute("suitableRooms",suitableRooms);
        log.trace("Set request attribute suitableRooms: " + suitableRooms);

        log.debug("Command finished");
        return Path.PAGE__PLAN_OFFER_BY_WISH;
    }
}
