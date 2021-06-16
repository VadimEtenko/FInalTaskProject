package project.web.command.requesteWish;

import project.db.RequestWishDao;
import project.db.entity.ClassOfRooms;
import project.db.entity.RequestWish;
import project.db.entity.User;
import project.web.Path;
import project.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateRequestWishCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            RequestWishDao requestWishDao = new RequestWishDao();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            LocalDate time_in = new Date(sdf.parse(request.getParameter("time_in")).getTime()).toLocalDate();
            LocalDate time_out = new Date(sdf.parse(request.getParameter("time_out")).getTime()).toLocalDate();
            long classId = Long.parseLong(request.getParameter("classId"));
            int numberOfBeds = Integer.parseInt(request.getParameter("numberOfBeds"));
            Long userId = ((User)request.getSession().getAttribute("user")).getId();

            requestWishDao.createRequestWish(userId, classId, numberOfBeds, time_in, time_out);

        }catch (ParseException e){
            e.printStackTrace();
        }
        return Path.COMMAND__LIST_FREE_ROOMS;
    }
}
