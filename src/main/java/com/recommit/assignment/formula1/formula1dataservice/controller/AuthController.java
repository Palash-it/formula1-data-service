package com.recommit.assignment.formula1.formula1dataservice.controller;

import com.recommit.assignment.formula1.formula1dataservice.dto.requests.AuthenticationRequest;
import com.recommit.assignment.formula1.formula1dataservice.dto.responses.AuthenticationResponse;
import com.recommit.assignment.formula1.formula1dataservice.security.service.UserSecurityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserSecurityService userSecurityService;

    @PostMapping(value = "/token")
    @Operation(summary = "Authenticate and get JWT Token")
    public ResponseEntity<?> loginAndGenerateToken(@RequestBody AuthenticationRequest authenticationRequest) {
        log.info("====[User login and token generate request received]=====Username:{}", authenticationRequest.getUsername());
        AuthenticationResponse authenticationResponse = userSecurityService.loginAndGenerateToken(authenticationRequest);
        return ResponseEntity.ok().body(authenticationResponse);
    }
}
