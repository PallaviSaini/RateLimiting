/*
package com.rateLimiter.ratelimitersolution.filter;


import com.rateLimiter.ratelimitersolution.service.IRateLimitingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RateLimitingFilter extends GenericFilterBean {

    @Autowired
    private IRateLimitingService rateLimitingService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String key = prepareKeyForBucket(req);
        if(rateLimitingService.consumeBucket(key)) {
            filterChain.doFilter(req, res);
        }else{
            res.setContentType("text/plain");
            res.setStatus(429);
            res.getWriter().append("Too many requests");
        }
    }

    private String prepareKeyForBucket(HttpServletRequest req) {
        String apiName = req.getRequestURI();
        StringBuilder stringBuilder = new StringBuilder(apiName);
        String userName = req.getParameter("userName");
        stringBuilder.append("~");
        stringBuilder.append(userName);
        return stringBuilder.toString();
    }
}
*/
