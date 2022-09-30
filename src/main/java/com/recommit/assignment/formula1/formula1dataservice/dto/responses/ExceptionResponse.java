package com.recommit.assignment.formula1.formula1dataservice.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private Date timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
