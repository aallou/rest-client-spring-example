package com.reach5.client.identityclient.apiclient.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
@Slf4j
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {

        return httpResponse.getStatusCode().series() == CLIENT_ERROR ||
               httpResponse.getStatusCode().series() == SERVER_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {

        String body = IOUtils.toString(httpResponse.getBody(), StandardCharsets.UTF_8);
        log.error("Status code : " + httpResponse.getStatusCode());
        log.error("Data : " + body);

        if (httpResponse.getStatusCode().series() == SERVER_ERROR) {
            throw new UnhandledException(httpResponse.getStatusCode(), body);

        } else if (httpResponse.getStatusCode().series() == CLIENT_ERROR) {
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NotFoundException("Not found error");
            }
            throw new UnhandledException(httpResponse.getStatusCode(), body);
        }
    }
}
