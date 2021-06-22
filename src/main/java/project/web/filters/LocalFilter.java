package project.web.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LocalFilter implements Filter {

    private static final Logger log = Logger.getLogger(LocalFilter.class);

    private String defaultLocale;

    public void init(FilterConfig config) throws ServletException {
        log.debug("Registration filer was initialized");
        defaultLocale = "en";
    }

    @Override
    public void destroy() {
        log.debug("LocalFilter destroyed!");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
            throws IOException, ServletException {
        log.debug("Local filter starts");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        if (null == session.getAttribute("defaultLocale")) {
            log.debug("Session's attribute DefaultLocale isn't initialized, set: " + defaultLocale);
            session.setAttribute("defaultLocale", defaultLocale);
        }
        log.debug("Local filter finish");
        next.doFilter(request, response);
    }

}


