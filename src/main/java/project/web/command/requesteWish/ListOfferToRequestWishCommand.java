package project.web.command.requesteWish;

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
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestWish rw = new RequestWishDao().findRequestWishById(
                Long.parseLong(request.getParameter("wishId")));
        request.setAttribute("wish",rw);
        List<Room> suitableRooms = new RoomDao().findAllFreeRoomsByCriteria(rw.getClass_id(),rw.getNumber_of_beds());
        request.setAttribute("suitableRooms",suitableRooms);
        return Path.PAGE__PLAN_OFFER_BY_WISH;
    }
}
