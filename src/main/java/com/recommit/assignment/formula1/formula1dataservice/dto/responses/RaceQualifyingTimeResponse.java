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
public class RaceQualifyingTimeResponse extends ErgastApiBaseResponse implements Serializable {

    private List<RaceQualifyingTimeDTO> raceQualifyingTime;

    @Data
    @NoArgsConstructor
    public static class RaceQualifyingTimeDTO {
        private String driverGivenName;
        private String driverFamilyName;
        private String q1;
        private String q2;
        private String q3;
    }
}
