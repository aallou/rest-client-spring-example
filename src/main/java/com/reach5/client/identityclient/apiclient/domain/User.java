package com.reach5.client.identityclient.apiclient.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class User implements Serializable {

    String password;
}
