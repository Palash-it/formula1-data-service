package com.recommit.assignment.formula1.formula1dataservice.service;

import com.recommit.assignment.formula1.formula1dataservice.converters.ErgastResponseConverter;
import com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse.*;
import com.recommit.assignment.formula1.formula1dataservice.dto.responses.SeasonFinalStandingResponse;
import com.recommit.assignment.formula1.formula1dataservice.exceptions.TooManyRequestException;
import com.recommit.assignment.formula1.formula1dataservice.serviceImpl.SeasonsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class SeasonsServiceTest {

    private final String season = "2022";
    private final Integer limit = 30;
    private final Integer offset = 0;

    @InjectMocks
    private SeasonsService seasonsService;

    private ErgastApiResponseDTO ergastApiResponseDTO;
    private SeasonFinalStandingResponse seasonFinalStandingResponse;
    private SeasonFinalStandingResponse.SeasonFinalStandings seasonFinalStandings;

    @Mock
    private ErgastApiService ergastApiService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ErgastResponseConverter ergastResponseConverter;

    @BeforeEach
    public void setUp() {
        ergastApiResponseDTO = new ErgastApiResponseDTO();

        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setGivenName("Max");

        ConstructorsDTO constructorsDTO = new ConstructorsDTO();
        constructorsDTO.setName("BMW");

        DriverStandingsDTO driverStandingsDTO = new DriverStandingsDTO();
        driverStandingsDTO.setDriver(driverDTO);
        driverStandingsDTO.setConstructors(Collections.singletonList(constructorsDTO));

        ErgastStandingsDTO ergastStandingsDTO = new ErgastStandingsDTO();
        ergastStandingsDTO.setDriverStandings(Collections.singletonList(driverStandingsDTO));

        ErgastStandingsTableDTO ergastStandingsTableDTO = new ErgastStandingsTableDTO();
        ergastStandingsTableDTO.setStandingsLists(Collections.singletonList(ergastStandingsDTO));

        ErgastApiResponseDTO.MRData mrData = new ErgastApiResponseDTO.MRData();
        mrData.setStandingsTable(ergastStandingsTableDTO);
        mrData.setSeries("2022");
        mrData.setTotal(100);
        mrData.setLimit(30);
        mrData.setUrl("test");
        mrData.setOffset(0);
        ergastApiResponseDTO.setMRData(mrData);

        seasonFinalStandings = new SeasonFinalStandingResponse.SeasonFinalStandings();
        seasonFinalStandings.setDriverGivenName(driverDTO.getGivenName());
        seasonFinalStandings.setConstructorName(constructorsDTO.getName());

        seasonFinalStandingResponse = new SeasonFinalStandingResponse();
        seasonFinalStandingResponse.setSeasonFinalStandingsList(Collections.singletonList(seasonFinalStandings));

    }

    @DisplayName("Should return null when params are wrong")
    @Test
    public void SeasonFinalStandingResponseTest() throws TooManyRequestException {
        given(ergastApiService.findDriverStandingsBySeason(season, limit, offset)).willReturn(ergastApiResponseDTO);
        given(modelMapper.map(ergastApiResponseDTO.getMRData(), SeasonFinalStandingResponse.class)).willReturn(seasonFinalStandingResponse);
        given(ergastResponseConverter.convertErgastFinalStandings(ergastApiResponseDTO.getMRData()
                .getStandingsTable().getStandingsLists().get(0).getDriverStandings())).willReturn(Collections.singletonList(seasonFinalStandings));
        SeasonFinalStandingResponse seasonFinalStandingResponse = seasonsService.getFinalStandingsBySeason(season, limit, offset);

        assertThat(seasonFinalStandingResponse.getSeasonFinalStandingsList().get(0).getDriverGivenName()).isEqualTo("Max");

    }
}
