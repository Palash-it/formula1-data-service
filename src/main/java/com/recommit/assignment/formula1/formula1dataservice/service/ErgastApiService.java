package com.recommit.assignment.formula1.formula1dataservice.service;

import com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse.ErgastApiResponseDTO;
import com.recommit.assignment.formula1.formula1dataservice.exceptions.TooManyRequestException;

/**
 * We have a rate limiter to make call to Ergast API
 * Any data the serves from local cache we will not block request for that
 */
public interface ErgastApiService {

    /**
     * Fetch all seasons from https://ergast.com/api/f1/seasons.json
     *
     * @param limit
     * @param offset
     * @return
     */
    ErgastApiResponseDTO findAllSeasons(Integer limit, Integer offset) throws TooManyRequestException;

    /**
     * http://ergast.com/api/f1/2008/driverStandings.json
     *
     * @param season
     * @param limit
     * @param offset return SeasonFinalStandingsDTO
     */
    ErgastApiResponseDTO findDriverStandingsBySeason(String season, Integer limit, Integer offset) throws TooManyRequestException;

    /**
     * http://ergast.com/api/f1/2012.json
     *
     * @param season
     * @param limit
     * @param offset return ErgastApiResponseDTO
     */
    ErgastApiResponseDTO findAllRacesBySeason(String season, Integer limit, Integer offset) throws TooManyRequestException;

    /**
     * http://ergast.com/api/f1/2008/5/qualifying
     * To list the qualifying results for a specific race use the following URL with the required year and round number:
     *
     * @param season
     * @param raceRound
     * @param limit
     * @param offset
     * @return
     */
    ErgastApiResponseDTO findRaceQualifyingResults(String season, Integer raceRound, Integer limit, Integer offset) throws TooManyRequestException;

    /**
     * http://ergast.com/api/f1/2008/5/results.json
     * To list the results for a specific race use the following URL with the required year and round number
     *
     * @param season
     * @param raceRound
     * @param limit
     * @param offset
     * @return
     */
    ErgastApiResponseDTO findRaceResults(String season, Integer raceRound, Integer limit, Integer offset) throws TooManyRequestException;
}
