package com.recommit.assignment.formula1.formula1dataservice.serviceImpl;


import com.recommit.assignment.formula1.formula1dataservice.dto.pointscoringsystem.Points;
import com.recommit.assignment.formula1.formula1dataservice.dto.pointscoringsystem.ScoringSystem;
import com.recommit.assignment.formula1.formula1dataservice.dto.pointscoringsystem.ScoringSystemAndSeasonMapping;
import com.recommit.assignment.formula1.formula1dataservice.dto.pointscoringsystem.ScoringSystemProperty;
import com.recommit.assignment.formula1.formula1dataservice.dto.responses.RaceResultsResponse;
import com.recommit.assignment.formula1.formula1dataservice.exceptions.UserDefinedException;
import com.recommit.assignment.formula1.formula1dataservice.helpers.PointScoringReaderHelper;
import com.recommit.assignment.formula1.formula1dataservice.service.PointsScoringSystemsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class will apply points scoring system on race results
 * We will read a JSON file with all points base on race position.
 */
@Service
@Slf4j
public class PositionalPointsScoringSystem implements PointsScoringSystemsService {

    private static final Logger logger = LoggerFactory.getLogger(PositionalPointsScoringSystem.class.getName());
    private final ScoringSystemProperty scoringSystemProperty;
    @Autowired
    private MessageSource messageSource;

    public PositionalPointsScoringSystem() {
        scoringSystemProperty = PointScoringReaderHelper.getInstance(false).getScoringSystemProperties();
    }

    /**
     * Apply provided scoring system on given Race Results
     * First Find the proper scoring system from points source file by given scoring season
     * Find the points for appropriate position for a specific race
     * Right now we support two category 1. positional points and 2. fastestLap point
     *
     * @param raceResults
     * @param scoringSeason
     * @return
     * @throws UserDefinedException
     */
    @Override
    public List<RaceResultsResponse.RaceResultsDTO> applyScoringSystem(final List<RaceResultsResponse.RaceResultsDTO> raceResults, String scoringSeason) throws UserDefinedException {
        Points matchedPointSystem = getPointsSystem(scoringSeason);
        List<RaceResultsResponse.RaceResultsDTO> raceResultsAfterApplyingNewScoring =
                raceResults.stream().map(raceResult -> {
                    int fastestLapPoint = matchedPointSystem.getFastestLap();
                    Optional<Points.PositionalPoints> positionalPoint = matchedPointSystem.getPositionalPoints().stream()
                            .filter(point -> point.getPosition().equals(raceResult.getPosition()))
                            .findFirst();
                    if (positionalPoint.isPresent()) {
                        if (!ObjectUtils.isEmpty(raceResult.getFastestLapRank()) && raceResult.getFastestLapRank() == 1) {
                            raceResult.setPoints(fastestLapPoint + positionalPoint.get().getPoint());
                        } else {
                            raceResult.setPoints(positionalPoint.get().getPoint());
                        }
                    } else {
                        raceResult.setPoints(0f);
                    }
                    return raceResult;
                }).collect(Collectors.toCollection(LinkedList::new));
        logger.info("====[Applied scoring system from Season :{}]====", scoringSeason);
        return raceResultsAfterApplyingNewScoring;
    }

    /**
     * Find the Points from points-scoring-system.json by scoring season
     *
     * @param scoringSeason
     * @return
     * @throws UserDefinedException
     */
    public Points getPointsSystem(String scoringSeason) throws UserDefinedException {
        String formulaName = getScoringFormalNameBySeason(scoringSeason);
        if (!ObjectUtils.isEmpty(formulaName)) {
            ScoringSystem scoringSystem = getScoringSystemByFormula(formulaName);
            return scoringSystem.getPoints();
        }
        logger.error("====[No formula found for season :{}]====", scoringSeason);
        throw new UserDefinedException(messageSource.getMessage("points.scoringSystem.notFound=",
                new String[]{scoringSeason}, LocaleContextHolder.getLocale()));
    }

    /**
     * Find the formula for the give scoringSeason
     * Will should have a default scoring formula for no matching found
     *
     * @param scoringSeason
     * @return
     */
    public String getScoringFormalNameBySeason(String scoringSeason) {
        ScoringSystemAndSeasonMapping seasonMapping = scoringSystemProperty.getScoringSystemAndSeasonMapping().stream()
                .filter(seasonMap -> seasonMap.getSeasons().contains(scoringSeason))
                .findFirst().orElse(null);
        if (!ObjectUtils.isEmpty(seasonMapping)) {
            return seasonMapping.getScoringSystem();
        }
        logger.error("====[No formula found for season :{}, Return" +
                "ing default formula]====", scoringSeason);
        return scoringSystemProperty.getDefaultFormula();
    }

    /**
     * We know which formula will be applied.
     * Now find the scoring system by provided formula name
     * Throw a proper message if no points scoring system is found
     *
     * @param scoringFormula
     * @return
     */
    private ScoringSystem getScoringSystemByFormula(String scoringFormula) throws UserDefinedException {
        ScoringSystem pointsScoringSystem = scoringSystemProperty.getScoringSystem().stream()
                .filter(scoringSystem -> scoringSystem.getFormula().equals(scoringFormula))
                .findFirst().orElseThrow(() -> new UserDefinedException(
                        messageSource.getMessage("points.scoringSystem.notFound=",
                                new String[]{scoringFormula}, LocaleContextHolder.getLocale())
                ));
        return pointsScoringSystem;
    }


    public ScoringSystemProperty getScoringSystemProperty() {
        return scoringSystemProperty;
    }
}
