package com.reach5.client.identityclient.apiclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reach5.client.identityclient.apiclient.domain.PasswordlessBody;
import com.reach5.client.identityclient.apiclient.exception.RestTemplateResponseErrorHandler;
import com.reach5.client.identityclient.apiclient.exception.UnhandledException;
import com.reach5.client.identityclient.config.ApiClientConfig;
import com.reach5.client.identityclient.config.ApiClientConfigProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

@RunWith(SpringRunner.class)
@RestClientTest(Reach5IdentityApiClient.class)
@Import({ApiClientConfig.class, ApiClientConfigProperties.class, RestTemplateResponseErrorHandler.class})
public class Reach5IdentityApiClientTest {

    @Autowired
    Reach5IdentityApiClient reach5IdentityApiClient;

    @Autowired
    MockRestServiceServer server;

    @Autowired
    ApiClientConfigProperties properties;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void can_create_user() throws JsonProcessingException {
        server.expect(MockRestRequestMatchers.requestTo(properties.getReach5().getBaseUrl() + "/identity/v1/passwordless/start"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andExpect(MockRestRequestMatchers.content().json(objectMapper.writeValueAsString(mockPasswordlessBody())))
                .andRespond(MockRestResponseCreators.withNoContent());

        reach5IdentityApiClient.createUserPasswordLess(mockPasswordlessBody());
    }

    @Test(expected = UnhandledException.class)
    public void cannot_create_user_error_server() throws JsonProcessingException {
        server.expect(MockRestRequestMatchers.requestTo(properties.getReach5().getBaseUrl() + "/identity/v1/passwordless/start"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.SERVICE_UNAVAILABLE));

        reach5IdentityApiClient.createUserPasswordLess(mockPasswordlessBody());
    }

    public PasswordlessBody mockPasswordlessBody() {
        return PasswordlessBody.builder()
                .client_id(properties.getReach5().getClientId())
                .scope("openid profile email phone")
                .authType("magic_link")
                .email("test-passwordless@test.com")
                .response_type("code")
                .redirect_uri("http://localhost:3001/login/callback").build();
    }
}
