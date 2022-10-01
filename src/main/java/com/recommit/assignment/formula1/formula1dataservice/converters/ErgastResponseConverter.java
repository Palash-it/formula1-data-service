package com.recommit.assignment.formula1.formula1dataservice.converters;

import com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse.DriverStandingsDTO;
import com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse.ErgastRaceQualifyingResultsDTO;
import com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse.ErgastRaceResultsDTO;
import com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse.ErgastRaceTableDTO;
import com.recommit.assignment.formula1.formula1dataservice.dto.responses.RaceQualifyingResultResponse;
import com.recommit.assignment.formula1.formula1dataservice.dto.responses.RaceResponse;
import com.recommit.assignment.formula1.formula1dataservice.dto.responses.SeasonFinalStandingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ErgastResponseConverter {

    /**
     * Convert ErgastSeasonFinalStandings DriverStandingsDTO to List of SeasonFinalStandingResponse.SeasonFinalStandings
     * Use a LinkedList to keep order same as data source
     *
     * @param driverStandingsDTOList
     * @return List<SeasonFinalStandingResponse.SeasonFinalStandings>
     */
    public List<SeasonFinalStandingResponse.SeasonFinalStandings> convertErgastFinalStandings(List<DriverStandingsDTO> driverStandingsDTOList) {
        LinkedList<SeasonFinalStandingResponse.SeasonFinalStandings> seasonFinalStanding = driverStandingsDTOList
                .stream().map(driverStandings -> {
                    SeasonFinalStandingResponse.SeasonFinalStandings seasonFinalStandingResponse = new SeasonFinalStandingResponse.SeasonFinalStandings();
                    seasonFinalStandingResponse.setPosition(driverStandings.getPosition());
                    seasonFinalStandingResponse.setPoints(driverStandings.getPoints());
                    seasonFinalStandingResponse.setDriverFamilyName(driverStandings.getDriver().getFamilyName());
                    seasonFinalStandingResponse.setDriverGivenName(driverStandings.getDriver().getGivenName());
                    seasonFinalStandingResponse.setConstructorName(driverStandings.getConstructors().get(0).getName());
                    seasonFinalStandingResponse.setConstructorNationality(driverStandings.getConstructors().get(0).getNationality());

                    return seasonFinalStandingResponse;
                }).collect(Collectors.toCollection(LinkedList::new));

        return seasonFinalStanding;
    }

    /**
     * Convert ErgastRaceTableDTO to RaceResponse.Race
     * We want to send only required information to UI
     *
     * @param raceTableDTO
     * @return RaceResponse.Race
     */
    public List<RaceResponse.Race> convertErgastRacesResponse(ErgastRaceTableDTO raceTableDTO) {
        LinkedList<RaceResponse.Race> races =
                raceTableDTO.getRaces().stream().map(race -> {
                    RaceResponse.Race raceResponse = new RaceResponse.Race();
                    raceResponse.setRound(race.getRound());
                    raceResponse.setRaceUrl(race.getUrl());
                    raceResponse.setRaceName(race.getRaceName());

                    raceResponse.setCircuitId(race.getCircuit().getCircuitId());
                    raceResponse.setCircuitName(race.getCircuit().getCircuitName());
                    raceResponse.setCircuitUrl(race.getCircuit().getCircuitUrl());

                    raceResponse.setLocality(race.getCircuit().getLocation().getLocality());
                    raceResponse.setCountry(race.getCircuit().getLocation().getCountry());
                    raceResponse.setDate(race.getCircuit().getDate());
                    raceResponse.setTime(race.getCircuit().getTime());

                    return raceResponse;
                }).collect(Collectors.toCollection(LinkedList::new));
        return races;
    }

    /**
     * Convert Ergast race qualifying time to UI required RaceQualifyingResultDTO
     * User LinkedList to better performance for data inserting
     *
     * @param QualifyingResults
     * @return
     */
    public List<RaceQualifyingResultResponse.RaceQualifyingResultDTO> convertRaceQualifyingTime(List<ErgastRaceQualifyingResultsDTO> QualifyingResults) {
        List<RaceQualifyingResultResponse.RaceQualifyingResultDTO> raceQualifyingResultList =
                QualifyingResults.stream().map(raceQualify -> {
                    RaceQualifyingResultResponse.RaceQualifyingResultDTO raceQualifyingDTO = new RaceQualifyingResultResponse.RaceQualifyingResultDTO();
                    raceQualifyingDTO.setDriverFamilyName(raceQualify.getDriver().getFamilyName());
                    raceQualifyingDTO.setDriverGivenName(raceQualify.getDriver().getGivenName());
                    raceQualifyingDTO.setQ1(raceQualify.getQ1());
                    raceQualifyingDTO.setQ2(raceQualify.getQ2());
                    raceQualifyingDTO.setQ3(raceQualify.getQ3());

                    return raceQualifyingDTO;
                }).collect(Collectors.toCollection(LinkedList::new));
        return raceQualifyingResultList;
    }

    /**
     * Convert Ergast List<ErgastRaceResultsDTO> to UI required Result List
     *
     * @param raceResultsDTOList
     * @return
     */
    public List<RaceQualifyingResultResponse.RaceQualifyingResultDTO> convertRaceResultData(List<ErgastRaceResultsDTO> raceResultsDTOList) {
        List<RaceQualifyingResultResponse.RaceQualifyingResultDTO> raceQualifyingResultList =
                raceResultsDTOList.stream().map(raceResult -> {
                    RaceQualifyingResultResponse.RaceQualifyingResultDTO raceResultDTO = new RaceQualifyingResultResponse.RaceQualifyingResultDTO();
                    raceResultDTO.setDriverFamilyName(raceResult.getDriver().getFamilyName());
                    raceResultDTO.setDriverGivenName(raceResult.getDriver().getGivenName());
                    raceResultDTO.setPoints(raceResult.getPoints());
                    raceResultDTO.setPosition(raceResult.getPosition());
                    raceResultDTO.setStatus(raceResult.getStatus());
                    raceResultDTO.setTime(!ObjectUtils.isEmpty(raceResult.getTime()) ? raceResult.getTime().getTime() : "");

                    return raceResultDTO;
                }).collect(Collectors.toCollection(LinkedList::new));
        return raceQualifyingResultList;
    }
}
