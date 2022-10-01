package com.recommit.assignment.formula1.formula1dataservice.converters;

import com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse.ErgastSeasonFinalStandingsDTO;
import com.recommit.assignment.formula1.formula1dataservice.dto.responses.SeasonFinalStandingResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ErgastResponseConverter {

    private final ModelMapper modelMapper;

    /**
     * Convert ErgastSeasonFinalStandingsDTO to List of SeasonFinalStandingResponse
     * Use a LinkedList to keep order same as data source
     *
     * @param ergastSeasonFinalStandings
     * @return SeasonFinalStandingResponse
     */
    public SeasonFinalStandingResponse convertErgastFinalStandings(ErgastSeasonFinalStandingsDTO ergastSeasonFinalStandings) {
        LinkedList<SeasonFinalStandingResponse.SeasonFinalStandings> seasonFinalStanding = ergastSeasonFinalStandings.getMRData().getStandingsTable().getStandingsLists().get(0).getDriverStandings()
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
        SeasonFinalStandingResponse finalStandingResponse = modelMapper.map(ergastSeasonFinalStandings.getMRData(), SeasonFinalStandingResponse.class);
        finalStandingResponse.setSeasonFinalStandingsList(seasonFinalStanding);

        return finalStandingResponse;
    }
}
