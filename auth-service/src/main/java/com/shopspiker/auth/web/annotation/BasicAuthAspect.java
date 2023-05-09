package com.shopspiker.auth.web.annotation;

import java.util.Base64;

import jakarta.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.shopspiker.auth.jpa.constants.AuthoritiesEnum;
import com.shopspiker.auth.jpa.entity.Client;
import com.shopspiker.auth.service.ClientService;
import com.shopspiker.common.web.exception.CustomApplicationException;

@Aspect
@Component
public class BasicAuthAspect {

    @Autowired
    private ClientService clientService;

    @Before("@annotation(com.shopspiker.auth.web.annotation.BasicAuth)")
    public void checkClientHasAccess(JoinPoint jp) throws Throwable {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest();

            // retrieving credentials the HTTP Authorization Header
            if (request.getHeader(HttpHeaders.AUTHORIZATION) == null
                    || !request.getHeader(HttpHeaders.AUTHORIZATION).startsWith("Basic"))
                throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, "UNAUTHENTICATED", "UNAUTHENTICATED");

            String authorizationCredentials = request.getHeader(HttpHeaders.AUTHORIZATION).substring("Basic".length())
                    .trim();

            // decoding credentials
            String[] decodedCredentials = new String(Base64.getDecoder().decode(authorizationCredentials)).split(":");

            // verifying if the credentials received are valid
            Client client = clientService.findByIdAndSecret(decodedCredentials[0], decodedCredentials[1]);

            BasicAuth basicAuth = ((MethodSignature) jp.getSignature()).getMethod().getAnnotation(BasicAuth.class);

            if (client.getAuthorities().contains(AuthoritiesEnum.valueOf(basicAuth.permission()))) {
                return;
            }

            throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, "UNAUTHENTICATED", "UNAUTHENTICATED");

        } catch (CustomApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR",
                    e.getLocalizedMessage());
        }
    }
}
