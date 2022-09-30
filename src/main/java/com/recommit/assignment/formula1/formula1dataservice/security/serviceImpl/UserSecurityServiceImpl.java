package com.recommit.assignment.formula1.formula1dataservice.security.serviceImpl;

import com.recommit.assignment.formula1.formula1dataservice.dto.requests.AuthenticationRequest;
import com.recommit.assignment.formula1.formula1dataservice.dto.responses.AuthenticationResponse;
import com.recommit.assignment.formula1.formula1dataservice.security.service.UserSecurityService;
import com.recommit.assignment.formula1.formula1dataservice.utils.JWTUtils;
import com.recommit.assignment.formula1.formula1dataservice.utils.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;


    @Override
    public AuthenticationResponse loginAndGenerateToken(AuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        AppUserDetailsImpl user = (AppUserDetailsImpl) authentication.getPrincipal();
        String ipAddress = getClientIpAddress();

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(JWTUtils.generateAccessToken(user, ipAddress));
        authenticationResponse.setExpiresIn(JWTUtils.ACCESS_TOKEN_EXPIRES_IN_MILLI_SECONDS);
        return authenticationResponse;
    }

    public String getClientIpAddress() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (!ObjectUtils.isEmpty(requestAttributes)) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            if (!ObjectUtils.isEmpty(servletRequestAttributes) && !ObjectUtils.isEmpty(servletRequestAttributes.getRequest())) {
                HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
                return Utility.getIPAddress(httpServletRequest);
            }
        }
        return "";
    }
}
