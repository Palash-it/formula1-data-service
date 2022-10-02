package com.recommit.assignment.formula1.formula1dataservice.controller;

import com.recommit.assignment.formula1.formula1dataservice.dto.responses.*;
import com.recommit.assignment.formula1.formula1dataservice.exceptions.UserDefinedException;
import com.recommit.assignment.formula1.formula1dataservice.serviceImpl.SeasonsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.Locale;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/seasons")
@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class SeasonsController {

    private final SeasonsService seasonsService;
    private final MessageSource messageSource;

    @Operation(description = "Find all seasons")
    @GetMapping()
    public ResponseEntity<?> findSeasons(@RequestParam(required = false, defaultValue = "100") @Max(value = 1000, message = "Maximum limit must be less than 1000") Integer limit,
                                         @RequestParam(required = false, defaultValue = "0") Integer pageNo) {
        SeasonResponse seasonResponse = seasonsService.getAllSeasons(limit, pageNo);
        if (!ObjectUtils.isEmpty(seasonResponse)) {
            BaseResponse<SeasonResponse> baseResponse =
                    new BaseResponse<>(
                            HttpStatus.OK.value(),
                            messageSource.getMessage("fetch.success", null, Locale.getDefault()),
                            seasonResponse);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }


    @Operation(description = "Find final Standings by season")
    @GetMapping(value = "/{season}/finalStandings")
    public ResponseEntity<?> findFinalStandings(@PathVariable(name = "season") String season,
                                                @RequestParam(required = false, defaultValue = "30") @Max(value = 1000, message = "Maximum limit must be less than 1000") Integer limit,
                                                @RequestParam(required = false, defaultValue = "0") Integer pageNo) {
        SeasonFinalStandingResponse seasonFinalStandingResponse = seasonsService.getFinalStandingsBySeason(season, limit, pageNo);
        if (!ObjectUtils.isEmpty(seasonFinalStandingResponse)) {
            BaseResponse<SeasonFinalStandingResponse> baseResponse =
                    new BaseResponse<>(
                            HttpStatus.OK.value(),
                            messageSource.getMessage("fetch.success", null, Locale.getDefault()),
                            seasonFinalStandingResponse);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @Operation(description = "Find all races by season")
    @GetMapping(value = "/{season}/races")
    public ResponseEntity<?> findRaces(@PathVariable(name = "season") String season,
                                       @RequestParam(required = false, defaultValue = "30") @Max(value = 1000, message = "Maximum limit must be less than 1000") Integer limit,
                                       @RequestParam(required = false, defaultValue = "0") Integer pageNo) {
        RaceResponse raceResponse = seasonsService.getRacesBySeason(season, limit, pageNo);
        if (!ObjectUtils.isEmpty(raceResponse)) {
            BaseResponse<RaceResponse> baseResponse =
                    new BaseResponse<>(
                            HttpStatus.OK.value(),
                            messageSource.getMessage("fetch.success", null, Locale.getDefault()),
                            raceResponse);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @Operation(description = "Find specific race's qualifying time")
    @GetMapping("/{season}/{round}/qualifying")
    public ResponseEntity<?> findRaceQualifyingTime(@PathVariable(name = "season") String season,
                                                    @PathVariable(name = "round") Integer round,
                                                    @RequestParam(required = false, defaultValue = "50") @Max(value = 1000, message = "Maximum limit must be less than 1000") Integer limit,
                                                    @RequestParam(required = false, defaultValue = "0") Integer pageNo) {
        RaceQualifyingTimeResponse raceQualifyingTime = seasonsService.getRaceQualifyingTime(season, round, limit, pageNo);
        if (!ObjectUtils.isEmpty(raceQualifyingTime)) {
            BaseResponse<RaceQualifyingTimeResponse> baseResponse =
                    new BaseResponse<>(
                            HttpStatus.OK.value(),
                            messageSource.getMessage("fetch.success", null, Locale.getDefault()),
                            raceQualifyingTime);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @Operation(description = "Find specific race's results")
    @GetMapping("/{season}/{round}/results")
    public ResponseEntity<?> findRaceResults(@PathVariable(name = "season") String season,
                                             @PathVariable(name = "round") Integer round,
                                             @RequestParam(required = false, defaultValue = "50") @Max(value = 1000, message = "Maximum limit must be less than 1000") Integer limit,
                                             @RequestParam(required = false, defaultValue = "0") Integer pageNo) {
        RaceResultsResponse raceResults = seasonsService.getRaceResults(season, round, limit, pageNo);
        if (!ObjectUtils.isEmpty(raceResults)) {
            BaseResponse<RaceResultsResponse> baseResponse =
                    new BaseResponse<>(
                            HttpStatus.OK.value(),
                            messageSource.getMessage("fetch.success", null, Locale.getDefault()),
                            raceResults);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @Operation(description = "Apply any points scoring system from list")
    @PostMapping("/{season}/{round}/apply-points-scoring-system")
    public ResponseEntity<?> applyPointsScoringSystemOnRaceResults(
            @PathVariable(name = "season") String season,
            @PathVariable(name = "round") Integer round,
            @RequestParam(name = "scoringSeason", required = false) String scoringSeason,
            @RequestParam(required = false, defaultValue = "50") @Max(value = 1000, message = "Maximum limit must be less than 1000") Integer limit,
            @RequestParam(required = false, defaultValue = "0") Integer pageNo) throws UserDefinedException {

        RaceResultsResponse raceResults = seasonsService.getRaceResultsAndApplyProvidedPointsScoringSystem(season, round, limit, pageNo, scoringSeason);
        if (!ObjectUtils.isEmpty(raceResults)) {
            BaseResponse<RaceResultsResponse> baseResponse =
                    new BaseResponse<>(
                            HttpStatus.OK.value(),
                            messageSource.getMessage("fetch.success", null, Locale.getDefault()),
                            raceResults);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
