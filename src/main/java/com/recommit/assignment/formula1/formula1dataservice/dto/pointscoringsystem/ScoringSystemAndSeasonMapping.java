package com.recommit.assignment.formula1.formula1dataservice.dto.pointscoringsystem;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ScoringSystemAndSeasonMapping {
    private List<String> seasons;
    private String scoringSystem;
}
