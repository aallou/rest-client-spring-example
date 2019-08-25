package com.reach5.client.identityclient.apiclient.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PasswordlessBody implements Serializable {

    private String client_id;
    private String scope;
    private String authType;
    private String email;
    private String response_type;
    private String redirect_uri;
}
