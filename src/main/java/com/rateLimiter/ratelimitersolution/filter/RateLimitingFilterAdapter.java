package com.rateLimiter.ratelimitersolution.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class RateLimitingFilterAdapter implements WebMvcConfigurer {

   /* @Autowired
    private RateLimitingFilter rateLimitingFilter;

    @Bean
    public FilterRegistrationBean rateFilterRegistrationBean() {
        FilterRegistrationBean regBean = new FilterRegistrationBean();
        regBean.setFilter(rateLimitingFilter);
        regBean.addUrlPatterns("/api/*");
        return regBean;
    }*/
}
