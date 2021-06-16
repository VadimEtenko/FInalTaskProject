package project.web.command.requesteWish;

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
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<RequestWish> requestWishList= new RequestWishDao().findAllRequestWish();
        request.setAttribute("requestWishList", requestWishList);
        return Path.PAGE__REQUEST_WISH_LIST;

    }
}
