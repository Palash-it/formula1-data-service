package com.recommit.assignment.formula1.formula1dataservice.service;

import com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse.ErgastSeasonFinalStandingsDTO;

public interface ErgastApiService {

    void findAllSeasons();

    /**
     * http://ergast.com/api/f1/2008/driverStandings.json
     *
     * @param season
     * @param limit
     * @param offset return SeasonFinalStandingsDTO
     */
    ErgastSeasonFinalStandingsDTO findDriverStandingsBySeason(String season, Integer limit, Integer offset);

    /**
     * http://ergast.com/api/f1/2008/constructorStandings
     *
     * @param season
     */
    void findConstructorStandingsBySeason(Integer season);


    /**
     * http://ergast.com/api/f1/2012
     *
     * @param season
     */
    void findAllRacesBySeason(Integer season);
}
