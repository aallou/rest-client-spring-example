package com.reach5.client.identityclient.apiclient;

import com.reach5.client.identityclient.apiclient.domain.PasswordlessBody;
import com.reach5.client.identityclient.apiclient.domain.RequestTokenBody;
import com.reach5.client.identityclient.apiclient.domain.TokenResponse;
import com.reach5.client.identityclient.apiclient.domain.User;
import com.reach5.client.identityclient.apiclient.interceptors.BearerAuthenticationInterceptor;
import com.reach5.client.identityclient.config.ApiClientConfig;
import com.reach5.client.identityclient.config.ApiClientConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Reach5IdentityApiClient {

    RestTemplate restTemplate;

    RestTemplate restTemplateBasicAuthentication;

    @Autowired
    ApiClientConfigProperties properties;

    public Reach5IdentityApiClient(RestTemplateBuilder restTemplateBuilder, ApiClientConfig apiClientConfig) {
        restTemplate = restTemplateBuilder.configure(apiClientConfig.reach5RestTemplateWithoutAuthorization());
        restTemplateBasicAuthentication = restTemplateBuilder.configure(apiClientConfig.reach5RestTemplateWithBasicAuthorization());
    }

    public void createUserPasswordLess(PasswordlessBody passwordlessBody) {
        restTemplate.postForEntity("/identity/v1/passwordless/start", passwordlessBody, Void.class);
    }

    public String getAccessTokenFromCodeAuthorization(String code) {
        RequestTokenBody requestBody = RequestTokenBody.builder()
                .grant_type("authorization_code")
                .code(code)
                .scope("openid offline_access profile read:custom")
                .redirect_uri("http://localhost:8080")
                .build();

        ResponseEntity<TokenResponse> response = restTemplateBasicAuthentication.postForEntity(properties.getReach5().getTokenUrl(), requestBody, TokenResponse.class);

        return response.getBody().getAccess_token();
    }

    public void updateUserPassword(String accessToken, String password) {
        restTemplate.getInterceptors().add(new BearerAuthenticationInterceptor(accessToken));
        restTemplate.postForEntity("/identity/v1/update-password",
                                    User.builder().password(password).build(), Void.class);
    }
}
