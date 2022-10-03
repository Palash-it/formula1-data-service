package com.recommit.assignment.formula1.formula1dataservice.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse.ErgastApiBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
public class RaceResultsResponse extends ErgastApiBaseResponse implements Serializable {
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
