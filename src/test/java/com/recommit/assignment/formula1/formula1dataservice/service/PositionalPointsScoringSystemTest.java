package com.recommit.assignment.formula1.formula1dataservice.service;

import com.recommit.assignment.formula1.formula1dataservice.dto.responses.RaceResultsResponse;
import com.recommit.assignment.formula1.formula1dataservice.exceptions.UserDefinedException;
import com.recommit.assignment.formula1.formula1dataservice.serviceImpl.PositionalPointsScoringSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PositionalPointsScoringSystemTest {

    private final Float fistPositionPointWithFastestLap = 26f;
    @InjectMocks
    private PositionalPointsScoringSystem positionalPointsScoringSystem;
    private List<RaceResultsResponse.RaceResultsDTO> raceResults;

    @BeforeEach
    public void setup() {
        RaceResultsResponse.RaceResultsDTO raceResultsDTO = new RaceResultsResponse.RaceResultsDTO();
        raceResultsDTO.setPoints(10f);
        raceResultsDTO.setPosition("1");
        raceResultsDTO.setFastestLapRank(1);

        raceResults = new LinkedList<>();
        raceResults.add(raceResultsDTO);
    }


    @DisplayName("When no formula found it should return formula as name")
    @Test
    public void getScoringFormalNameBySeasonTest() {
        String formulaName = positionalPointsScoringSystem.getScoringFormalNameBySeason("1999");

        assertThat(formulaName).isEqualTo("formula1");
    }

    @DisplayName("Apply formula 2022 over provided race result list")
    @Test
    public void applyScoringSystemTest() throws UserDefinedException {
        System.out.println("Before new scoring apply point is : " + raceResults.get(0).getPoints());

        List<RaceResultsResponse.RaceResultsDTO> afterApplyResults = positionalPointsScoringSystem.applyScoringSystem(raceResults, "2022");

        assertThat(afterApplyResults.get(0).getPoints()).isEqualTo(fistPositionPointWithFastestLap);
    }

}
