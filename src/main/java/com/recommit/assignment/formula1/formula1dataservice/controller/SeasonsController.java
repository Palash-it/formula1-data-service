package com.recommit.assignment.formula1.formula1dataservice.controller;

import com.recommit.assignment.formula1.formula1dataservice.dto.responses.BaseResponse;
import com.recommit.assignment.formula1.formula1dataservice.dto.responses.SeasonFinalStandingResponse;
import com.recommit.assignment.formula1.formula1dataservice.serviceImpl.SeasonsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findSeasons() {
        return null;
    }


    @Operation(description = "Find final findings by season")
    @GetMapping(value = "/{season}/finalFindings")
    public ResponseEntity<?> findSeasons(@PathVariable(name = "season") String season,
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

}
