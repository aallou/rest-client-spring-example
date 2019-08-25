package com.reach5.client.identityclient;

import com.reach5.client.identityclient.apiclient.Reach5IdentityApiClient;
import com.reach5.client.identityclient.apiclient.domain.PasswordlessBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandRunner implements CommandLineRunner {

    @Autowired
    Reach5IdentityApiClient reach5IdentityApiClient;

    @Override
    public void run(String... args) throws Exception {
        PasswordlessBody passwordlessBody = PasswordlessBody.builder()
                .client_id("xxx")
                .scope("openid profile email phone")
                .authType("magic_link")
                .email("test-passwordless"+ Math.random() +"@test.com")
                .response_type("code")
                .redirect_uri("http://localhost:3001/login/callback").build();

        reach5IdentityApiClient.createClientPasswordLess(passwordlessBody);
    }
}
