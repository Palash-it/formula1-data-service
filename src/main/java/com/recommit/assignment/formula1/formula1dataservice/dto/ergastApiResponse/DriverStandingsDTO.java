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
public class DriverStandingsDTO implements Serializable {
    private Integer position;
    private String positionText;
    private Float points;
    private Integer wins;
    @JsonProperty("Driver")
    private DriverDTO driver;
    @JsonProperty("Constructors")
    private List<ConstructorsDTO> constructors;
}
