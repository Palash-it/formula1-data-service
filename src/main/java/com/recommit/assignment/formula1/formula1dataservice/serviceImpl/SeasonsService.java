package com.recommit.assignment.formula1.formula1dataservice.serviceImpl;

import com.recommit.assignment.formula1.formula1dataservice.configurations.ServiceProperties;
import com.recommit.assignment.formula1.formula1dataservice.converters.ErgastResponseConverter;
import com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse.ErgastSeasonFinalStandingsDTO;
import com.recommit.assignment.formula1.formula1dataservice.dto.responses.SeasonFinalStandingResponse;
import com.recommit.assignment.formula1.formula1dataservice.service.ErgastApiService;
import com.recommit.assignment.formula1.formula1dataservice.utils.Utility;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class SeasonsService {

    private static final Logger logger = LoggerFactory.getLogger(SeasonsService.class);
    private final ErgastApiService ergastApiService;
    private final ErgastResponseConverter ergastResponseConverter;
    private final ServiceProperties serviceProperties;

    /**
     * Fetch final standings data from ErgastApiService and build your own POJO as per requirements
     * calculate the offset by limit and pageNo
     * Send offset to ErgastApiService
     *
     * @param season
     * @return SeasonFinalStandingResponse
     */
    public SeasonFinalStandingResponse getFinalStandingsBySeason(String season, Integer limit, Integer pageNo) {
        Integer offset = Utility.getOffsetByLimitAndPageNo(limit, pageNo);
        ErgastSeasonFinalStandingsDTO ergastSeasonFinalStandings = ergastApiService.findDriverStandingsBySeason(season, limit, offset);
        if (!ObjectUtils.isEmpty(ergastSeasonFinalStandings)
                && !ObjectUtils.isEmpty(ergastSeasonFinalStandings.getMRData().getStandingsTable().getStandingsLists())) {
            logger.info("====[Data found: {}]====", ergastSeasonFinalStandings);
            return ergastResponseConverter.convertErgastFinalStandings(ergastSeasonFinalStandings);
        }
        return null;
    }
}
