package com.recommit.assignment.formula1.formula1dataservice.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RaceResultsResponse {
    private List<RaceResultsDTO> raceResults;

    @Data
    @NoArgsConstructor
    public static class RaceResultsDTO {
        private String driverGivenName;
        private String driverFamilyName;
        private String position;
        private String time;
        private String status;
        private Float points;
        private Integer fastestLapRank;
    }
}
