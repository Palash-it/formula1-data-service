package com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErgastRacesDTO {
    private Integer season;
    private Integer round;
    private String url;
    private String raceName;

    @JsonProperty("Circuit")
    private CircuitDTO circuit;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CircuitDTO {
        private String circuitId;
        @JsonProperty("url")
        private String circuitUrl;
        private String circuitName;
        @JsonProperty("Location")
        private Location location;
        private String date;
        private String time;

        @Data
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Location {
            private String lat;
            @JsonProperty("long")
            private String longitude;
            private String locality;
            private String country;
        }
    }


}
