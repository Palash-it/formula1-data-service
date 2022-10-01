package com.recommit.assignment.formula1.formula1dataservice.serviceImpl;

import com.recommit.assignment.formula1.formula1dataservice.configurations.ServiceProperties;
import com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse.ErgastSeasonFinalStandingsDTO;
import com.recommit.assignment.formula1.formula1dataservice.helpers.RestTemplateHelper;
import com.recommit.assignment.formula1.formula1dataservice.service.ErgastApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class ErgastApiServiceImpl implements ErgastApiService {

    private final ServiceProperties serviceProperties;
    private final RestTemplateHelper restTemplateHelper;

    @Override
    public void findAllSeasons() {

    }

    @Override
    public ErgastSeasonFinalStandingsDTO findDriverStandingsBySeason(String season, Integer limit, Integer offset) {
        if (!ObjectUtils.isEmpty(season)) {
            URI uri = UriComponentsBuilder.fromHttpUrl(serviceProperties.getErgastApi().getBaseUrl())
                    .path("f1/{season}/driverStandings.json")
                    .queryParam("limit", limit)
                    .queryParam("offset", offset)
                    .build(season);
            return restTemplateHelper.getForEntity(ErgastSeasonFinalStandingsDTO.class, uri);
        }
        return null;
    }

    @Override
    public void findConstructorStandingsBySeason(Integer season) {

    }

    @Override
    public void findAllRacesBySeason(Integer season) {

    }
}
