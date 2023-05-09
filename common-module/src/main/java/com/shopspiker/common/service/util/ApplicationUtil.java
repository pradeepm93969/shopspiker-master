package com.shopspiker.common.service.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.shopspiker.common.web.exception.CustomApplicationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

public class ApplicationUtil {

    public static String getJsonStringFromObject(Object obj) {
        ObjectMapper Obj = new ObjectMapper();
        Obj.setSerializationInclusion(Include.NON_NULL);
        Obj.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try {
            return Obj.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getIp(HttpServletRequest request) {
        return request.getHeader("X-Forwarded-For") == null ?
                request.getRemoteAddr() :
                request.getHeader("X-Forwarded-For").split(",")[0];
    }

    public static Long getUserIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, "Authentication Not found",
                    "Authentication Not found");
        }
        return Long.parseLong(authentication.getName());
    }
}
