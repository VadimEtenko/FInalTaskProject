package project.web.filters;

import org.apache.log4j.Logger;
import project.Path;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

    private static final Logger log = Logger.getLogger(LoginFilter.class);

    public LoginFilter() {

    }

    @Override
    public void init(FilterConfig fConfig) {
        log.debug("LoginFilter initialized!");
    }

    @Override
    public void destroy() {
        log.debug("LoginFilter destroyed!");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        log.debug("Login filter start");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        // Check, is user  log in, and is he try to log in
        if (session.getAttribute("user") == null &&
                request.getParameter("command") != null &&
                (!request.getParameter("command").equals("login")
                        && !request.getParameter("command").equals("logout")
                        && !request.getParameter("command").equals("registration"))) {
            //Forward to login page
            log.debug("User wasn't logged");
            RequestDispatcher dispatcher = request.getRequestDispatcher(Path.PAGE__LOGIN);
            dispatcher.forward(request, response);
        } else {
            if (session.getAttribute("user") != null)
                log.info("User --> " + session.getAttribute("user") + " is logged");
            chain.doFilter(request, response);
        }
        log.debug("Login filter finish");
    }

}