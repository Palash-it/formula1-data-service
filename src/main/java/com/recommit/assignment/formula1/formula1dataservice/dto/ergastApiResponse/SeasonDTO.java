package com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SeasonDTO implements Comparable<SeasonDTO> {
    private Integer season;
    private String url;

    @Override
    public int compareTo(SeasonDTO season) {
        if (getSeason() == null || season.getSeason() == null) {
            return 0;
        }
        return getSeason().compareTo(season.getSeason());
    }
}
