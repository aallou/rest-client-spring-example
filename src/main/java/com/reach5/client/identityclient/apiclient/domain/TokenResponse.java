package com.reach5.client.identityclient.apiclient.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {

    String id_token;
    String access_token;
    String refresh_token;
    String token_type;
    Long expires_in;
}
