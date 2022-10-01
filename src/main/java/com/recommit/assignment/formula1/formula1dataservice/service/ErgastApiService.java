package com.recommit.assignment.formula1.formula1dataservice.service;

import com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse.ErgastApiResponseDTO;

public interface ErgastApiService {

    /**
     * Fetch all seasons from https://ergast.com/api/f1/seasons.json
     * @param limit
     * @param offset
     * @return
     */
    ErgastApiResponseDTO findAllSeasons(Integer limit, Integer offset);

    /**
     * http://ergast.com/api/f1/2008/driverStandings.json
     *
     * @param season
     * @param limit
     * @param offset return SeasonFinalStandingsDTO
     */
    ErgastApiResponseDTO findDriverStandingsBySeason(String season, Integer limit, Integer offset);

    /**
     * http://ergast.com/api/f1/2008/constructorStandings
     * will implement later
     *
     * @param season
     */
    void findConstructorStandingsBySeason(Integer season);


    /**
     * http://ergast.com/api/f1/2012.json
     *
     * @param season
     * @param limit
     * @param offset return ErgastApiResponseDTO
     */
    ErgastApiResponseDTO findAllRacesBySeason(String season, Integer limit, Integer offset);
}
