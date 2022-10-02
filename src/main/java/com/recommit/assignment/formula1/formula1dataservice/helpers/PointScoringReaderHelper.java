package com.recommit.assignment.formula1.formula1dataservice.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recommit.assignment.formula1.formula1dataservice.dto.pointscoringsystem.ScoringSystemProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class PointScoringReaderHelper {

    private static final Logger logger = LoggerFactory.getLogger(PointScoringReaderHelper.class.getName());
    private static PointScoringReaderHelper instance;
    private static ScoringSystemProperty scoringSystemProperties;

    private PointScoringReaderHelper() {

    }

    public static PointScoringReaderHelper getInstance(boolean reload) {
        if (instance == null || reload) {
            instance = new PointScoringReaderHelper();

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                scoringSystemProperties = objectMapper.readValue(new ClassPathResource("points-scoring-system.json").getFile(), ScoringSystemProperty.class);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }

        return instance;
    }

    public ScoringSystemProperty getScoringSystemProperties() {
        return scoringSystemProperties;
    }

}
