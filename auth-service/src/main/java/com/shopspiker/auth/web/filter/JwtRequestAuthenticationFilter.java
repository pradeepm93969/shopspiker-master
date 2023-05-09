package com.shopspiker.auth.web.filter;

import com.shopspiker.auth.jpa.constants.JwtTokenTypeEnum;
import com.shopspiker.auth.service.util.JwtUtil;
import com.shopspiker.common.web.exception.CustomApplicationException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtRequestAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        try {
            // JWT Token is in the form "Bearer token". Remove Bearer word and
            // get only the Token

            if (jwtUtil.extractJwtFromRequest() != null &&
                    jwtUtil.checkCurrentTokenType(JwtTokenTypeEnum.ACCESS_TOKEN)) {
                UserDetails userDetails = new
                        User(jwtUtil.getSubjectFromToken(), "",
                        jwtUtil.getRolesFromToken());

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } catch (ExpiredJwtException ex) {
            log.error("Token is Expired");
            throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, "TOKEN_EXPIRED", "token expired");
        } catch (Exception ex) {
            log.error("Unable to authenticate the token");
            throw new CustomApplicationException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "INTERNAL_SERVER_ERROR", ex.getLocalizedMessage());
        }
        chain.doFilter(request, response);
    }

}