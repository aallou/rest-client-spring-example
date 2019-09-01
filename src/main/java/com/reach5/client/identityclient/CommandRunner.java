package com.reach5.client.identityclient;

import com.reach5.client.identityclient.apiclient.Reach5IdentityApiClient;
import com.reach5.client.identityclient.config.ApiClientConfigProperties;
import com.reach5.client.identityclient.service.JwksVerifier;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Log
public class CommandRunner implements CommandLineRunner {

    @Autowired
    Reach5IdentityApiClient reach5IdentityApiClient;

    String jwksUrl = "url/jwks.json";
    String token = "jwt.token";

    @Autowired
    ApiClientConfigProperties properties;

    @Override
    public void run(String... args) throws Exception {
        System.out.print(JwksVerifier.validateJwts(jwksUrl, token));
    }

}
