package com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErgastRaceResultsDTO {
    private String position;
    private Integer number;
    private Integer points;
    @JsonProperty("Driver")
    private DriverDTO driver;
    @JsonProperty("Constructor")
    private ConstructorsDTO constructor;
    private Integer grid;
    private Integer laps;
    private String status;
    @JsonProperty("Time")
    private RaceFinishTime time;

}
