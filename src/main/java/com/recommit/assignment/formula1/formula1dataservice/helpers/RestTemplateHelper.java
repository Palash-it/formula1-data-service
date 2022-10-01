package com.recommit.assignment.formula1.formula1dataservice.helpers;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

/**
 * This RestTemplateHelper is the entry point to make any rest call. It will help to reduce boilerplate codes
 * like exception handling, logging, data exchange
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RestTemplateHelper {

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateHelper.class);

    private final RestTemplate restTemplate;


    /**
     * @param clazz
     * @param uri
     * @param <T>
     * @return
     */
    public <T> T getForEntity(Class<T> clazz, URI uri) {
        T result = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            log.info("====[API URL: {}]====", uri);
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
            if (response.getStatusCode() == HttpStatus.OK) {
                try {
                    result = objectMapper.readValue(response.getBody(), javaType);
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            } else {
                logger.info("====[No data found {}]====", response.getStatusCode());
            }
            return result;
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.info("====[No data found {}]====", uri.toString());
            } else {
                logger.info("====[rest client exception]====", exception.getMessage());
            }
        }
        return null;
    }
}
