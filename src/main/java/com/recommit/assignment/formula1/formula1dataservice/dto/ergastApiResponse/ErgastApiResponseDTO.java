package com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErgastApiResponseDTO implements Serializable {

    @JsonProperty("MRData")
    private MRData mRData;

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MRData extends ErgastApiBaseResponse implements Serializable {
        @JsonProperty("StandingsTable")
        private ErgastStandingsTableDTO standingsTable;
        @JsonProperty("RaceTable")
        private ErgastRaceTableDTO raceTable;
    }

}
