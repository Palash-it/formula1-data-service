package com.recommit.assignment.formula1.formula1dataservice.controller;

import com.recommit.assignment.formula1.formula1dataservice.dto.pointscoringsystem.ScoringSystemProperty;
import com.recommit.assignment.formula1.formula1dataservice.dto.responses.BaseResponse;
import com.recommit.assignment.formula1.formula1dataservice.serviceImpl.PositionalPointsScoringSystem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/points-scoring-system")
@RestController
@RequiredArgsConstructor
@Slf4j
public class PointScoringSystemController {

    private final PositionalPointsScoringSystem positionalPointsScoringSystem;
    private final MessageSource messageSource;

    @GetMapping()
    public ResponseEntity<?> getAllPointsScoringSystem() {
        ScoringSystemProperty scoringSystemProperty = positionalPointsScoringSystem.getScoringSystemProperty();
        if (!ObjectUtils.isEmpty(scoringSystemProperty)) {
            BaseResponse<ScoringSystemProperty> baseResponse =
                    new BaseResponse<>(
                            HttpStatus.OK.value(),
                            messageSource.getMessage("fetch.success", null, Locale.getDefault()),
                            scoringSystemProperty);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
