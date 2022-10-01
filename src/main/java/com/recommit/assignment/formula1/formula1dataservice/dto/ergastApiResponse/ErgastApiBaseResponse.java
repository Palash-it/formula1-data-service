package com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErgastApiBaseResponse {
    private String series;
    private String url;
    private Integer limit;
    private Integer offset;
    private Integer total;

}
