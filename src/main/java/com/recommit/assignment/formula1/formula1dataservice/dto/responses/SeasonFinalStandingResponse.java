package com.recommit.assignment.formula1.formula1dataservice.dto.responses;

import com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse.ErgastApiBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SeasonFinalStandingResponse extends ErgastApiBaseResponse implements Serializable {

    private List<SeasonFinalStandings> seasonFinalStandingsList;

    @Data
    @NoArgsConstructor
    public static class SeasonFinalStandings {
        private Integer position;

        private String driverGivenName;
        private String driverFamilyName;
        private Integer points;

        private String constructorName;
        private String constructorNationality;
    }
}
