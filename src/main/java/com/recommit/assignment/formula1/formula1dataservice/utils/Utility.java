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

    /**
     * Page no 1 or 0 both means first page
     *
     * @param limit
     * @param pageNo
     * @return
     */
    public static Integer getOffsetByLimitAndPageNo(Integer limit, Integer pageNo) {
        int offset = 0;
        if (pageNo == 0 || pageNo == 1) {
            offset = 0;
        } else {
            offset = limit * (pageNo - 1);
        }
        return offset;
    }
}
