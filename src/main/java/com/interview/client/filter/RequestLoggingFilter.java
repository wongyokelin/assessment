package com.interview.client.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req =
                (HttpServletRequest) request;

        ContentCachingResponseWrapper wrappedResponse =
                new ContentCachingResponseWrapper(
                        (HttpServletResponse) response);

        long startTime = System.currentTimeMillis();

        log.info(
                "Incoming Request => Method: {}, URI: {}",
                req.getMethod(),
                req.getRequestURI()
        );

        chain.doFilter(request, wrappedResponse);

        long duration =
                System.currentTimeMillis() - startTime;

        String responseBody =
                new String(
                        wrappedResponse.getContentAsByteArray(),
                        StandardCharsets.UTF_8
                );

        log.info(
                "Outgoing Response => Status: {}, Duration: {} ms, Body: {}",
                wrappedResponse.getStatus(),
                duration,
                responseBody
        );

        wrappedResponse.copyBodyToResponse();
    }
}