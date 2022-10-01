package com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
public class ErgastRaceQualifyingResultsDTO extends ErgastRaceResultsDTO {
    @JsonProperty("Q1")
    private String q1;
    @JsonProperty("Q2")
    private String q2;
    @JsonProperty("Q3")
    private String q3;
}
