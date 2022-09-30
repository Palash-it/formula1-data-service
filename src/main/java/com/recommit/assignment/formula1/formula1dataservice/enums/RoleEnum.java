package com.recommit.assignment.formula1.formula1dataservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleEnum {
    ROLE_ADMIN("ROLE_ADMIN", "All access"),
    ROLE_USER("ROLE_USER", "Limited access");

    private String role;
    private String description;
}
