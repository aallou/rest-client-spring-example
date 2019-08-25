package com.reach5.client.identityclient.config;

import com.reach5.client.identityclient.apiclient.exception.RestTemplateResponseErrorHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class ApiClientConfig {

    @Autowired
    ApiClientConfigProperties apiClientConfigProperties;

    @Autowired
    RestTemplateResponseErrorHandler restTemplateResponseErrorHandler;

    public RestTemplate reach5RestTemplateWithoutAuthorization () {
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(apiClientConfigProperties.getReach5().getBaseUrl()));
        restTemplate.setErrorHandler(restTemplateResponseErrorHandler);
        return restTemplate;
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5000;
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setSocketTimeout(timeout)
                .build();
        CloseableHttpClient client = HttpClientBuilder
                .create()
                .setDefaultRequestConfig(config)
                .build();
        return new HttpComponentsClientHttpRequestFactory(client);
    }
}
