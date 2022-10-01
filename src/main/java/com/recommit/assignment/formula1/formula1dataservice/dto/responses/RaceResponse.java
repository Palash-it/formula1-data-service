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
public class RaceResponse extends ErgastApiBaseResponse implements Serializable {
    private List<Race> races;

    @Data
    @NoArgsConstructor
    public static class Race {
        private Integer round;
        private String raceUrl;
        private String raceName;

        private String circuitId;
        private String circuitName;
        private String circuitUrl;

        private String locality;
        private String country;
        private String date;
        private String time;
    }
}
