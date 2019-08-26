package com.reach5.client.identityclient.apiclient.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestTokenBody {

    private String grant_type;
    private String scope;
    private String code;
    private String redirect_uri;

}
