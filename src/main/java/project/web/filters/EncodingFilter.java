package project.web.filters;

import org.apache.log4j.Logger;
import project.web.command.LoginCommand;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.*;

public class EncodingFilter implements Filter {

    private static final Logger log = Logger.getLogger(EncodingFilter.class);

    private String encoding;

    public void init(FilterConfig config) throws ServletException
    {
        log.debug("Encoding filer was initialized");
        encoding = config.getInitParameter("requestEncoding");
        if( encoding==null ) {
            encoding = "UTF-8";
            log.info("Encoding set to UTF-8");
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain       next)
            throws IOException, ServletException
    {
        // Respect the client-specified character encoding
        // (see HTTP specification section 3.4.1)
        if(null == request.getCharacterEncoding()) {
            request.setCharacterEncoding(encoding);
        }


        /**
         * Set the default response content type and encoding
         */
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");


        next.doFilter(request, response);
    }

    public void destroy(){}
}
