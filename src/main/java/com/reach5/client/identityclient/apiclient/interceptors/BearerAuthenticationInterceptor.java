package com.reach5.client.identityclient.apiclient.interceptors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;

import java.io.IOException;

public class BearerAuthenticationInterceptor implements ClientHttpRequestInterceptor {

    private final String token;

    public BearerAuthenticationInterceptor(String token) {
        Assert.notNull(token, "Token must not be null");
        this.token = token;
    }

    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        if (!headers.containsKey("Authorization")) {
            headers.set("Authorization", " Bearer " + token);
        }

        return execution.execute(request, body);
    }
}