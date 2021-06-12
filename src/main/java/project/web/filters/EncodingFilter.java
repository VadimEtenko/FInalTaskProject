package project.web.filters;

import org.apache.log4j.Logger;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {

    private static final Logger log = Logger.getLogger(EncodingFilter.class);

    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
        log.debug("EncodingFilter initialized!");
    }

    @Override
    public void destroy() {
        log.debug("EncodingFilter destroyed!");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        if (req.getCharacterEncoding() == null) {
            req.setCharacterEncoding(encoding);
            log.debug("EncodingFilter set character encoding!");
        }
        chain.doFilter(req, resp);
    }

}
