package com.recommit.assignment.formula1.formula1dataservice.serviceImpl;

import com.recommit.assignment.formula1.formula1dataservice.converters.ErgastResponseConverter;
import com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse.ErgastApiResponseDTO;
import com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse.SeasonDTO;
import com.recommit.assignment.formula1.formula1dataservice.dto.responses.*;
import com.recommit.assignment.formula1.formula1dataservice.exceptions.UserDefinedException;
import com.recommit.assignment.formula1.formula1dataservice.service.ErgastApiService;
import com.recommit.assignment.formula1.formula1dataservice.service.PointsScoringSystemsService;
import com.recommit.assignment.formula1.formula1dataservice.utils.Utility;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SeasonsService {

    private static final Logger logger = LoggerFactory.getLogger(SeasonsService.class);
    static Map<String, Object> dataStore = new HashMap<>();
    private final ErgastApiService ergastApiService;
    private final ErgastResponseConverter ergastResponseConverter;
    private final PointsScoringSystemsService pointsScoringSystemsService;
    private final ModelMapper modelMapper;

    /**
     * Fetch final standings data from ErgastApiService and build your own POJO as per requirements
     * calculate the offset by limit and pageNo
     * Send offset to ErgastApiService
     *
     * @param season
     * @return SeasonFinalStandingResponse
     */
    @Cacheable(value = "cacheFinalStandingsBySeason")
    public SeasonFinalStandingResponse getFinalStandingsBySeason(String season, Integer limit, Integer pageNo) {
        Integer offset = Utility.getOffsetByLimitAndPageNo(limit, pageNo);
        ErgastApiResponseDTO ergastApiResponseDTO = ergastApiService.findDriverStandingsBySeason(season, limit, offset);
        if (!ObjectUtils.isEmpty(ergastApiResponseDTO) && !ObjectUtils.isEmpty(ergastApiResponseDTO.getMRData().getStandingsTable().getStandingsLists())) {
            SeasonFinalStandingResponse finalStandingResponse = modelMapper.map(ergastApiResponseDTO.getMRData(), SeasonFinalStandingResponse.class);
            List<SeasonFinalStandingResponse.SeasonFinalStandings> seasonFinalStandings = ergastResponseConverter.convertErgastFinalStandings(ergastApiResponseDTO.getMRData().getStandingsTable().getStandingsLists().get(0).getDriverStandings());
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
    @Cacheable(value = "cacheRacesBySeason")
    public RaceResponse getRacesBySeason(String season, Integer limit, Integer pageNo) {
        Integer offset = Utility.getOffsetByLimitAndPageNo(limit, pageNo);
        ErgastApiResponseDTO ergastApiResponseDTO = ergastApiService.findAllRacesBySeason(season, limit, offset);
        if (!ObjectUtils.isEmpty(ergastApiResponseDTO) && !ObjectUtils.isEmpty(ergastApiResponseDTO.getMRData().getRaceTable().getRaces())) {
            RaceResponse raceResponse = modelMapper.map(ergastApiResponseDTO.getMRData(), RaceResponse.class);
            List<RaceResponse.Race> races = ergastResponseConverter.convertErgastRacesResponse(ergastApiResponseDTO.getMRData().getRaceTable());
            raceResponse.setRaces(races);
            return raceResponse;
        }
        return null;
    }

    /**
     * Fetch all season
     * Sort those data by season to show the latest first.
     *
     * @param limit
     * @param pageNo
     * @return
     */
    @Cacheable(value = "cacheAllSeasons")
    public SeasonResponse getAllSeasons(Integer limit, Integer pageNo) {
        Integer offset = Utility.getOffsetByLimitAndPageNo(limit, pageNo);
        ErgastApiResponseDTO ergastApiResponseDTO = ergastApiService.findAllSeasons(limit, offset);
        if (!ObjectUtils.isEmpty(ergastApiResponseDTO) && !ObjectUtils.isEmpty(ergastApiResponseDTO.getMRData().getSeasonTable().getSeasons())) {
            SeasonResponse seasonResponse = modelMapper.map(ergastApiResponseDTO.getMRData(), SeasonResponse.class);
            List<SeasonDTO> seasons = ergastApiResponseDTO.getMRData().getSeasonTable().getSeasons();
            Collections.reverse(seasons);
            seasonResponse.setSeasons(seasons);
            return seasonResponse;
        }
        return null;
    }

    /**
     * Find a race's qualifying time
     *
     * @param season
     * @param round
     * @param limit
     * @param pageNo
     * @return
     */
    public RaceQualifyingTimeResponse getRaceQualifyingTime(String season, Integer round, Integer limit, Integer pageNo) {
        Integer offset = Utility.getOffsetByLimitAndPageNo(limit, pageNo);
        ErgastApiResponseDTO ergastApiResponseDTO = ergastApiService.findRaceQualifyingResults(season, round, limit, offset);
        if (!ObjectUtils.isEmpty(ergastApiResponseDTO)
                && !ObjectUtils.isEmpty(ergastApiResponseDTO.getMRData().getRaceTable().getRaces())) {
            RaceQualifyingTimeResponse raceQualifyingResultResponse = modelMapper.map(ergastApiResponseDTO.getMRData(), RaceQualifyingTimeResponse.class);
            List<RaceQualifyingTimeResponse.RaceQualifyingTimeDTO> raceQualifyingData =
                    ergastResponseConverter.convertRaceQualifyingTime(ergastApiResponseDTO.getMRData()
                            .getRaceTable().getRaces().get(0).getQualifyingResults());
            raceQualifyingResultResponse.setRaceQualifyingTime(raceQualifyingData);
            return raceQualifyingResultResponse;
        }
        return null;
    }

    /**
     * Fetch a race's final results
     *
     * @param season
     * @param round
     * @param limit
     * @param pageNo
     * @return
     */
    @Cacheable(value = "cacheRaceResults")
    public RaceResultsResponse getRaceResults(String season, Integer round, Integer limit, Integer pageNo) {
        Integer offset = Utility.getOffsetByLimitAndPageNo(limit, pageNo);
        ErgastApiResponseDTO ergastApiResponseDTO = ergastApiService.findRaceResults(season, round, limit, offset);
        if (!ObjectUtils.isEmpty(ergastApiResponseDTO)
                && !ObjectUtils.isEmpty(ergastApiResponseDTO.getMRData().getRaceTable().getRaces())) {
            RaceResultsResponse raceResultResponse = modelMapper.map(ergastApiResponseDTO.getMRData(), RaceResultsResponse.class);
            List<RaceResultsResponse.RaceResultsDTO> raceResultsData =
                    ergastResponseConverter.convertRaceResultData(ergastApiResponseDTO.getMRData()
                            .getRaceTable().getRaces().get(0).getResults());
            raceResultResponse.setRaceResults(raceResultsData);
            return raceResultResponse;
        }
        return null;
    }

    /**
     * Fetch race results from Ergast API and apply a new points scoring system which provided by user
     * If new scoring season is null or same as data source season then skip re-calculation
     *
     * @param season        which season race will be fetched
     * @param round
     * @param limit
     * @param pageNo
     * @param scoringSeason which season's points scoring system will be applied
     * @return
     */
    @Cacheable(value = "cacheRaceResultsApplyingProvidedScoringSystem")
    public RaceResultsResponse getRaceResultsAndApplyProvidedPointsScoringSystem(
            String season, Integer round, Integer limit, Integer pageNo, String scoringSeason) throws UserDefinedException {
        RaceResultsResponse raceResultsResponse = getRaceResults(season, round, limit, pageNo);
        if (!ObjectUtils.isEmpty(raceResultsResponse) && !ObjectUtils.isEmpty(raceResultsResponse.getRaceResults())
                && (!ObjectUtils.isEmpty(scoringSeason) && !scoringSeason.equalsIgnoreCase(season))) {
            logger.info("====[Race results found. Go for applying a new scoring]====");
            List<RaceResultsResponse.RaceResultsDTO> resultsAfterApplyingPointsScoringSystem =
                    pointsScoringSystemsService.applyScoringSystem(raceResultsResponse.getRaceResults(), scoringSeason);
            raceResultsResponse.setRaceResults(resultsAfterApplyingPointsScoringSystem);
        }
        return raceResultsResponse;
    }

}
