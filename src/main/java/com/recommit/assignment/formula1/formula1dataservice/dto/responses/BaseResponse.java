package com.recommit.assignment.formula1.formula1dataservice.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private int responseCode;
    private String message;
    private T responseData;
}
