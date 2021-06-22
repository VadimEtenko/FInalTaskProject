package project.web.command.requesteWish;

import project.db.RequestWishDao;
import project.db.RoomDao;
import project.db.entity.RequestWish;
import project.db.entity.Room;
import project.db.entity.User;
import project.web.Path;
import project.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListOffersToVerifyCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User)request.getSession().getAttribute("user");
        RequestWishDao rwd = new RequestWishDao();

        List<RequestWish> userWishesList = rwd.findRequestWishByUserId(user.getId());
        request.setAttribute("userWishesList",userWishesList);

        List<Room> offeredRoomsList = new RoomDao().getOfferedRoomsByUserId(user.getId());
        request.setAttribute("offeredRoomsList", offeredRoomsList);

        return Path.PAGE__VERIFY_OFFERS;
    }
}
