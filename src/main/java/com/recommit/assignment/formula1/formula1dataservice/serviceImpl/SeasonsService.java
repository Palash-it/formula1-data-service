package com.recommit.assignment.formula1.formula1dataservice.serviceImpl;

import com.recommit.assignment.formula1.formula1dataservice.configurations.ServiceProperties;
import com.recommit.assignment.formula1.formula1dataservice.converters.ErgastResponseConverter;
import com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse.ErgastApiResponseDTO;
import com.recommit.assignment.formula1.formula1dataservice.dto.responses.RaceResponse;
import com.recommit.assignment.formula1.formula1dataservice.dto.responses.SeasonFinalStandingResponse;
import com.recommit.assignment.formula1.formula1dataservice.service.ErgastApiService;
import com.recommit.assignment.formula1.formula1dataservice.utils.Utility;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeasonsService {

    private static final Logger logger = LoggerFactory.getLogger(SeasonsService.class);
    private final ErgastApiService ergastApiService;
    private final ErgastResponseConverter ergastResponseConverter;
    private final ServiceProperties serviceProperties;
    private final ModelMapper modelMapper;

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
        ErgastApiResponseDTO ergastApiResponseDTO = ergastApiService.findDriverStandingsBySeason(season, limit, offset);
        if (!ObjectUtils.isEmpty(ergastApiResponseDTO)
                && !ObjectUtils.isEmpty(ergastApiResponseDTO.getMRData().getStandingsTable().getStandingsLists())) {
            SeasonFinalStandingResponse finalStandingResponse = modelMapper.map(ergastApiResponseDTO.getMRData(), SeasonFinalStandingResponse.class);
            List<SeasonFinalStandingResponse.SeasonFinalStandings> seasonFinalStandings = ergastResponseConverter.convertErgastFinalStandings(ergastApiResponseDTO.getMRData()
                    .getStandingsTable().getStandingsLists().get(0).getDriverStandings());
            finalStandingResponse.setSeasonFinalStandingsList(seasonFinalStandings);
            return finalStandingResponse;
        }
        return null;
    }

    /**
     * Fetch Race data from Ergast API and convert data to required DTO
     *
     * @param season
     * @param limit
     * @param pageNo
     * @return
     */
    public RaceResponse getRacesBySeason(String season, Integer limit, Integer pageNo) {
        Integer offset = Utility.getOffsetByLimitAndPageNo(limit, pageNo);
        ErgastApiResponseDTO ergastApiResponseDTO = ergastApiService.findAllRacesBySeason(season, limit, offset);
        if (!ObjectUtils.isEmpty(ergastApiResponseDTO)
                && !ObjectUtils.isEmpty(ergastApiResponseDTO.getMRData().getRaceTable().getRaces())) {
            RaceResponse raceResponse = modelMapper.map(ergastApiResponseDTO.getMRData(), RaceResponse.class);
            List<RaceResponse.Race> races = ergastResponseConverter.convertErgastRacesResponse(ergastApiResponseDTO.getMRData().getRaceTable());
            raceResponse.setRaces(races);
            return raceResponse;
        }
        return null;
    }
}
