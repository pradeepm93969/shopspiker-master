package com.shopspiker.common.web.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.f4b6a3.ulid.UlidCreator;
import com.shopspiker.common.configuration.CommonConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    @Autowired
    private CommonConfig commonConfig;

    public static Set<String> urlsToSkip = new HashSet<>(Arrays.asList(
            "swagger", "actuator", "api-docs", "/webjars/", "/css/", "/img/", "/js/",
            "favicon.ico", "public", "auth", "password"));

    public static boolean urlToBeSkipped(String url) {
        for (String skippedUrl : urlsToSkip) {
            if (url.contains(skippedUrl)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if (!urlToBeSkipped(request.getRequestURL().toString())) {
            ContentCachingRequestWrapper req = new ContentCachingRequestWrapper((HttpServletRequest) request);
            ContentCachingResponseWrapper res = new ContentCachingResponseWrapper((HttpServletResponse) response);
            if (!request.getRequestURI().contains("error")) {
                request.setAttribute("UNIQUE_REQUEST_ID",
                        UlidCreator.getUlid().toString());
            }
            try {
                filterChain.doFilter(req, res);
            } catch (Throwable t) {
                throw t;
            } finally {

                while (req.getInputStream().read() >= 0) ;

                if (commonConfig.isReqResLoggingEnabled()) {
                    String body = new String(req.getContentAsByteArray());
                    String responseBody = new String(res.getContentAsByteArray());

                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode tree = mapper.createObjectNode();
                    try {
                        tree = mapper.readTree(body);
                    } catch (Exception e) {
                    }

                    StringBuilder builder = new StringBuilder();
                    builder.append("\n");
                    builder.append("###############################################################").append("\n");
                    builder.append("[").append(req.getMethod()).append("] ").append(req.getRequestURI().toString())
                            .append(StringUtils.isBlank(req.getQueryString()) ? "" : "?" + req.getQueryString()).append("\n");
                    builder.append("HEADERS: ").append("UNIQUE_REQUEST_ID=").append(req.getAttribute("UNIQUE_REQUEST_ID"))
                            .append("       ,Authorization=").append(req.getHeader("Authorization")).append("\n");
                    builder.append("Remote Address: ").append(req.getRemoteAddr()).append("\n");
                    builder.append("Request Body: ").append(tree.toString()).append("\n");
                    builder.append("Response Status Code: ").append(res.getStatus()).append("\n");
                    builder.append("Response Body: ").append(responseBody).append("\n");
                    builder.append("###############################################################").append("\n");
                    if (responseBody != null && responseBody.trim().startsWith("<!DOCTYPE HTML>")) {
                        //Custom pages
                    } else if (responseBody != null && responseBody.trim().startsWith("<html>")) {
                        //OOTB pages
                    } else if (responseBody != null && responseBody.trim().startsWith("<div")) {
                        //OOTB pages
                    } else {
                        log.info(builder.toString());
                    }
                    res.copyBodyToResponse();
                }
                // One more point, in case of redirect this will be called twice! beware to handle that
                // somewhat
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}