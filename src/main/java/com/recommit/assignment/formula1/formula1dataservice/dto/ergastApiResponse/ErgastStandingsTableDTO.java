package com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErgastStandingsTableDTO implements Serializable {
    private Integer season;
    @JsonProperty("StandingsLists")
    private List<ErgastStandingsDTO> standingsLists;
}
