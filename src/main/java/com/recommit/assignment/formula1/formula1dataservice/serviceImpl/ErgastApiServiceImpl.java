package com.recommit.assignment.formula1.formula1dataservice.serviceImpl;

import com.recommit.assignment.formula1.formula1dataservice.configurations.ServiceProperties;
import com.recommit.assignment.formula1.formula1dataservice.dto.ergastApiResponse.ErgastApiResponseDTO;
import com.recommit.assignment.formula1.formula1dataservice.exceptions.TooManyRequestException;
import com.recommit.assignment.formula1.formula1dataservice.helpers.RestTemplateHelper;
import com.recommit.assignment.formula1.formula1dataservice.service.ErgastApiService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class ErgastApiServiceImpl implements ErgastApiService {

    private final Bucket bucket;
    @Autowired
    private ServiceProperties serviceProperties;
    @Autowired
    private RestTemplateHelper restTemplateHelper;
    @Autowired
    private MessageSource messageSource;

    /**
     * Initialize rate limiter Bucket condition
     */
    public ErgastApiServiceImpl() {
        this.bucket = Bucket.builder()
                .addLimit(Bandwidth.classic(4, Refill.intervally(4, Duration.ofSeconds(1))))
                .addLimit(Bandwidth.classic(200, Refill.intervally(200, Duration.ofHours(1))))
                .build();
    }

    /**
     * @param limit
     * @param offset
     * @return
     */
    @Override
    public ErgastApiResponseDTO findAllSeasons(Integer limit, Integer offset) throws TooManyRequestException {
        URI uri = UriComponentsBuilder.fromHttpUrl(serviceProperties.getErgastApi().getBaseUrl())
                .path("f1/seasons.json")
                .queryParam("limit", limit)
                .queryParam("offset", offset).build("");
        return getForEntity(ErgastApiResponseDTO.class, uri);
    }

    /**
     * @param season
     * @param limit
     * @param offset return SeasonFinalStandingsDTO
     * @return
     */
    @Override
    public ErgastApiResponseDTO findDriverStandingsBySeason(String season, Integer limit, Integer offset) throws TooManyRequestException {
        if (!ObjectUtils.isEmpty(season)) {
            URI uri = UriComponentsBuilder.fromHttpUrl(serviceProperties.getErgastApi().getBaseUrl())
                    .path("f1/{season}/driverStandings.json")
                    .queryParam("limit", limit)
                    .queryParam("offset", offset)
                    .build(season);
            return getForEntity(ErgastApiResponseDTO.class, uri);
        }
        return null;
    }

    /**
     * @param season
     * @param limit
     * @param offset return ErgastApiResponseDTO
     * @return
     */
    @Override
    public ErgastApiResponseDTO findAllRacesBySeason(String season, Integer limit, Integer offset) throws TooManyRequestException {
        if (!ObjectUtils.isEmpty(season)) {
            URI uri = UriComponentsBuilder.fromHttpUrl(serviceProperties.getErgastApi().getBaseUrl())
                    .path("f1/{season}.json")
                    .queryParam("limit", limit)
                    .queryParam("offset", offset)
                    .build(season);
            return getForEntity(ErgastApiResponseDTO.class, uri);
        }
        return null;
    }

    /**
     * @param season
     * @param raceRound
     * @param limit
     * @param offset
     * @return
     */
    @Override
    public ErgastApiResponseDTO findRaceQualifyingResults(String season, Integer raceRound, Integer limit, Integer offset) throws TooManyRequestException {
        if (!ObjectUtils.isEmpty(season) && !ObjectUtils.isEmpty(raceRound)) {
            URI uri = UriComponentsBuilder.fromHttpUrl(serviceProperties.getErgastApi().getBaseUrl())
                    .path("f1/{season}/{round}/qualifying.json")
                    .queryParam("limit", limit)
                    .queryParam("offset", offset)
                    .build(season, raceRound);
            return getForEntity(ErgastApiResponseDTO.class, uri);
        }
        return null;
    }

    /**
     * @param season
     * @param raceRound
     * @param limit
     * @param offset
     * @return
     */
    @Override
    public ErgastApiResponseDTO findRaceResults(String season, Integer raceRound, Integer limit, Integer offset) throws TooManyRequestException {
        if (!ObjectUtils.isEmpty(season) && !ObjectUtils.isEmpty(raceRound)) {
            URI uri = UriComponentsBuilder.fromHttpUrl(serviceProperties.getErgastApi().getBaseUrl())
                    .path("f1/{season}/{round}/results.json")
                    .queryParam("limit", limit)
                    .queryParam("offset", offset)
                    .build(season, raceRound);
            return getForEntity(ErgastApiResponseDTO.class, uri);
        }
        return null;
    }

    /**
     * This is the entry point for Ergast API call. We will add a rate limiter here
     *
     * @param clazz
     * @param uri
     * @param <T>
     * @return
     */
    public <T> T getForEntity(Class<T> clazz, URI uri) throws TooManyRequestException {
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            return restTemplateHelper.getForEntity(clazz, uri);
        }
        long waitForRefill = probe.getNanosToWaitForRefill() / 1000000000;
        throw new TooManyRequestException(messageSource.getMessage("toomany.request.blocked", new String[]{"" + waitForRefill}, LocaleContextHolder.getLocale()));
    }
}
