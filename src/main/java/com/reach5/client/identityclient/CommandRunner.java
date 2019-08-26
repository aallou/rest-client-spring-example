package com.reach5.client.identityclient;

import com.reach5.client.identityclient.apiclient.Reach5IdentityApiClient;
import com.reach5.client.identityclient.apiclient.domain.PasswordlessBody;
import com.reach5.client.identityclient.config.ApiClientConfigProperties;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Log
public class CommandRunner implements CommandLineRunner {

    @Autowired
    Reach5IdentityApiClient reach5IdentityApiClient;

    String code = "azeazea";

    @Autowired
    ApiClientConfigProperties properties;

    @Override
    public void run(String... args) throws Exception {
        String token = reach5IdentityApiClient.getAccessTokenFromCodeAuthorization(code);
        log.info("Token : " + token);

        reach5IdentityApiClient.updateUserPassword(token, "newpassword");
    }


}
