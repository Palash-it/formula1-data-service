package com.recommit.assignment.formula1.formula1dataservice.dto.pointscoringsystem;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Points {

    private List<PositionalPoints> positionalPoints;
    private Integer fastestLap;

    @Data
    @NoArgsConstructor
    public static class PositionalPoints {
        private String position; //position can be string sometimes. have seen into F1 API portal
        private Float point;
    }
}
