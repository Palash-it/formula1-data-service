package com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ErgastSeasonsTableDRO implements Serializable {

    @JsonProperty("Seasons")
    private List<SeasonDTO> seasons;
}
