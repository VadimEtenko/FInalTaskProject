package project.web.command;

import project.web.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FindFreeRoomsCommand extends Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return Path.PAGE__FIND_FREE_ROOM_LIST;
    }

    public String getForward(){
        return Path.PAGE__FIND_FREE_ROOM_LIST;
    }
}
