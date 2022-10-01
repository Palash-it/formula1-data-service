package com.recommit.assignment.formula1.formula1dataservice.dto.responses;

import com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse.ErgastApiBaseResponse;
import com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse.SeasonDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SeasonResponse extends ErgastApiBaseResponse implements Serializable {
    List<SeasonDTO> seasons;
}
