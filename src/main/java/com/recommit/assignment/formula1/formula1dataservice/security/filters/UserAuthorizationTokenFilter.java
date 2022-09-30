package com.recommit.assignment.formula1.formula1dataservice.security.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recommit.assignment.formula1.formula1dataservice.dto.responses.ExceptionResponse;
import com.recommit.assignment.formula1.formula1dataservice.exceptions.UserDefinedException;
import com.recommit.assignment.formula1.formula1dataservice.utils.ApplicationConstants;
import com.recommit.assignment.formula1.formula1dataservice.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserAuthorizationTokenFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getServletPath().startsWith("/user/auth/")) {
            String authorizationHeader = request.getHeader(ApplicationConstants.AUTHORIZATION);
            if (!ObjectUtils.isEmpty(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring("Bearer ".length());
                try {
                    DecodedJWT decodedJWT = JWTUtils.getVerifyAccessToken(token);
                    String username = decodedJWT.getSubject();
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (!userDetails.isEnabled()) {
                        log.error("[Username HAS BEEN DEACTIVATED :: {}]", username);
                        throw new UserDefinedException("Your account [{" + username + "}] has been deactivated. Please contact with your administration");
                    }
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } catch (Exception ex) {
                    log.error("[=====ERROR=====:: {},{}]", ex.getMessage(), ex);
                    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.FORBIDDEN.value(), ex.getMessage(), ex.getMessage(), request.getServletPath());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    new ObjectMapper().writeValue(response.getOutputStream(), exceptionResponse);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
