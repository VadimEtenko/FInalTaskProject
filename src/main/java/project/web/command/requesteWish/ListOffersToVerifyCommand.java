package project.web.command.requesteWish;

import org.apache.log4j.Logger;
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

    private static final Logger log = Logger.getLogger(ListOffersToVerifyCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command started");
        RequestWishDao rwd = new RequestWishDao();

        User user = (User) request.getSession().getAttribute("user");
        log.trace("Get session attribute user: " + user);

        List<RequestWish> userWishesList = rwd.findRequestWishByUserId(user.getId());
        log.trace("Find in database request wish list by user id: " + userWishesList);

        request.setAttribute("userWishesList", userWishesList);
        log.trace("Set session attribute userWishesList: " + userWishesList);

        List<Room> offeredRoomsList = new RoomDao().getOfferedRoomsByUserId(user.getId());
        log.trace("Find in database offered rooms: " + offeredRoomsList);

        request.setAttribute("offeredRoomsList", offeredRoomsList);
        log.trace("Set request attribute offeredRoomsList: " + offeredRoomsList);

        log.debug("Command finished");
        return Path.PAGE__VERIFY_OFFERS;
    }
}
