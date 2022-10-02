package com.recommit.assignment.formula1.formula1dataservice.service;

import com.recommit.assignment.formula1.formula1dataservice.dto.responses.RaceResultsResponse;
import com.recommit.assignment.formula1.formula1dataservice.exceptions.UserDefinedException;

import java.util.List;

public interface PointsScoringSystemsService {

    /**
     * Find the specific season scoring system and apply it on the RaceResults
     * @param source
     * @return
     */
    List<RaceResultsResponse.RaceResultsDTO> applyScoringSystem(final List<RaceResultsResponse.RaceResultsDTO> source, String scoreSeason) throws UserDefinedException;
}
