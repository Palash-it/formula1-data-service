package com.recommit.assignment.formula1.formula1dataservice.dto.pointscoringsystem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * For now I am fetching points scoring system from a json file. There may have json format/data error when update the file
 * We can use some online tool to verify correct json data
 * For more convenience, i will prefer a database system.
 */

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoringSystemProperty {
    private List<ScoringSystem> scoringSystem;
    private List<ScoringSystemAndSeasonMapping> scoringSystemAndSeasonMapping;
    private String defaultFormula;
}
