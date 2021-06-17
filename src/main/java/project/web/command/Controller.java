package project.web.command;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import project.web.Path;


/**
 * Main servlet controller.
 * Servlet mapping by web.xml
 */
public class Controller extends HttpServlet {

    private static final Logger log = Logger.getLogger(Controller.class);

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        startController(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        startController(request, response);
    }

    /**
     * Main method of this controller.
     */
    private void startController(HttpServletRequest request,
                                 HttpServletResponse response) throws IOException, ServletException {
        try {

            log.debug("Controller starts");

            // extract command name from the request
            String commandName = request.getParameter("command");
            log.trace("Request parameter: command --> " + commandName);

            System.out.println("make command" + commandName);

            // obtain command object by its name
            Command command = CommandContainer.get(commandName);
            log.trace("Obtained command --> " + command);

            // execute command and get forward address
            String forward = command.execute(request, response);
            log.trace("Forward address --> " + forward);

            // if the forward address is not null go to the address
            if (forward != null) {
                log.debug("Controller finished, now go to forward address --> " + forward);
                request.getSession().setAttribute("title", commandName);
                request.setAttribute("forward", forward);
                RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            String errorMessage = "Oops... Smth get wrong";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + Arrays.toString(e.getStackTrace()) + " ");
            RequestDispatcher dispatcher = request.getRequestDispatcher(Path.PAGE__ERROR_PAGE);
            dispatcher.forward(request, response);
        }


    }

}
