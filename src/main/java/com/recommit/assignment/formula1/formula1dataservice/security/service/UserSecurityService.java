package com.recommit.assignment.formula1.formula1dataservice.security.service;

import com.recommit.assignment.formula1.formula1dataservice.dto.requests.AuthenticationRequest;
import com.recommit.assignment.formula1.formula1dataservice.dto.responses.AuthenticationResponse;

public interface UserSecurityService {
    AuthenticationResponse loginAndGenerateToken(AuthenticationRequest authenticationRequest);
}
