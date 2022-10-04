package com.recommit.assignment.formula1.formula1dataservice.controller;

import com.recommit.assignment.formula1.formula1dataservice.dto.requests.AuthenticationRequest;
import com.recommit.assignment.formula1.formula1dataservice.security.service.UserSecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerWebMvcIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MessageSource messageSource;

    @MockBean
    private UserSecurityService userSecurityService;

    private AuthenticationRequest authenticationRequest;

    @BeforeEach
    public void setUp() {
        authenticationRequest = new AuthenticationRequest("nouser", "wrongPassword");

    }

    @DisplayName("wrong password test when generates token")
    @Test
    public void login_and_generate_token_return_403_when_gives_wrong_credentials_test() throws Exception {
        when(userSecurityService.loginAndGenerateToken(authenticationRequest))
                .thenThrow(new BadCredentialsException("Bad credentials"));
        mockMvc.perform(post("/api/v1/auth/token"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andExpect(unauthenticated());
    }

}
