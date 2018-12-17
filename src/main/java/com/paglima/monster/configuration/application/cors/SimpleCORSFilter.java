package com.paglima.monster.configuration.application.cors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(503)
public class SimpleCORSFilter implements Filter {

    private static final String ACCESS_CONTROL_ALLOW_ORIGIN  = "Access-Control-Allow-Origin";
    private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    private static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";

    @Value("${endpoints.cors.allowed-origins:*}")
    private String allowedOrigins;

    @Value("${endpoints.cors.allowed-methods:GET,POST,PUT,DELETE,OPTIONS,PATCH}")
    private String allowedMethods;

    @Value("${endpoints.cors.allowed-headers:x-requested-with,Content-Type,Pragma,Cache-Control,If-Modified-Since,X-Pricing-Token,Authorization}")
    private String allowedHeaders;

    @Value("${endpoints.cors.exposed-headers:X-Pricing-Token-Renewed,X-TID}")
    private String exposedHeaders;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (StringUtils.isNotEmpty(allowedOrigins)) {
            response.addHeader(ACCESS_CONTROL_ALLOW_ORIGIN, allowedOrigins);
        }

        if (StringUtils.isNotEmpty(allowedMethods)) {
            response.addHeader(ACCESS_CONTROL_ALLOW_METHODS, allowedMethods);
        }

        if (StringUtils.isNotEmpty(allowedHeaders)) {
            response.addHeader(ACCESS_CONTROL_ALLOW_HEADERS, allowedHeaders);
        }

        if (StringUtils.isNotEmpty(exposedHeaders)) {
            response.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS, exposedHeaders);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
