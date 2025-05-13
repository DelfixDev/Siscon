package com.siscon.siscontest.config.logging;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;


@Component
public class HttpRequestHeaderLoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestHeaderLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
        throws ServletException, IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("--- Request Headers ---");
            Enumeration<String> headerNames = req.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = req.getHeader(headerName);
                logger.debug("{}: {}", headerName, headerValue);
            }
            logger.debug("--- End of Request Headers ---");
        }
        chain.doFilter(req, res);
    }
}
