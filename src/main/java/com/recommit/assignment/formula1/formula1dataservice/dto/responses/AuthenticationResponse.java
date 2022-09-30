package com.recommit.assignment.formula1.formula1dataservice.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * In this class i used _(underscore for the prop name)
 * The reason is to keep match with postman auto capture.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private Long expiresIn;
    private String tokenType = "Always Bearer";
}
