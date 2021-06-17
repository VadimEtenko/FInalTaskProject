package project.web.command.requesteWish;

import project.db.OffersDao;
import project.db.RequestWishDao;
import project.web.Path;
import project.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateOfferCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long userId = Long.parseLong(request.getParameter("userId"));
        for(String suitableRoomIdString : request.getParameterValues("suitableRoomId")) {
            long roomId = Long.parseLong(suitableRoomIdString);
            new OffersDao().createOffer(userId, roomId);
        }
        new RequestWishDao().deleteRequestWishByIdUserId(userId);
        return Path.COMMAND__LIST_REQUEST_WISH;
    }
}
