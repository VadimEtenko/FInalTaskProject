package project.web.filters;

import org.apache.log4j.Logger;
import project.web.Path;
import project.db.entity.Role;
import project.db.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UnLoginFilter implements Filter {

    private static final Logger log = Logger.getLogger(UnLoginFilter.class);

    public UnLoginFilter() {
    }

    @Override
    public void init(FilterConfig fConfig) {
        log.debug("UnLogin Filter initialized!");
    }

    @Override
    public void destroy() {
        log.debug("UnLogin Filter destroyed!");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        log.debug("UnLogin Filter start");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        log.debug("Try to get User parameter from session");
        User user = (User) session.getAttribute("user");

        // Check, is user  log in, and is he try to log in
        if (user != null) {
            Role userRole = Role.getRole(user);
            log.trace("userRole --> " + userRole);

            String forward = Path.PAGE__LOGIN;

            if (userRole == Role.CLIENT)
                forward = Path.PAGE__FIND_FREE_ROOM_LIST;

            if (userRole == Role.MANAGER)
                forward = Path.COMMAND__LIST_REQUESTED;

            log.trace("userRole --> " + userRole);
            log.debug("Forward to --> " + forward);
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
        log.debug("UnLogin Filter finished");
    }

}
