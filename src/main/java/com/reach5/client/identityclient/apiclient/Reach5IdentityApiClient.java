package com.reach5.client.identityclient.apiclient;

import com.reach5.client.identityclient.apiclient.domain.PasswordlessBody;
import com.reach5.client.identityclient.config.ApiClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Reach5IdentityApiClient {

    RestTemplate restTemplate;

    public Reach5IdentityApiClient(RestTemplateBuilder restTemplateBuilder, ApiClientConfig apiClientConfig) {
        restTemplate = restTemplateBuilder.configure(apiClientConfig.reach5RestTemplateWithoutAuthorization());
    }

    public void createClientPasswordLess(PasswordlessBody passwordlessBody) {
        restTemplate.postForEntity("/identity/v1/passwordless/start", passwordlessBody, Void.class);
    }
}
