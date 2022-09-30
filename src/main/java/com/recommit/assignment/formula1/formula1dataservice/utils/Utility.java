package com.recommit.assignment.formula1.formula1dataservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class Utility {

    public static String getIPAddress(HttpServletRequest request) {
        if (!ObjectUtils.isEmpty(request)) {
            String remoteAddress = request.getHeader("X-FORWARDED-FOR");
            if (ObjectUtils.isEmpty(remoteAddress)) {
                return request.getRemoteAddr();
            }
            return remoteAddress;
        }
        return "";
    }
}
