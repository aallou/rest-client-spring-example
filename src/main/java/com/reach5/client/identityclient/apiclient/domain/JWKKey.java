package com.reach5.client.identityclient.apiclient.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JWKKey {

    String kty;
    String e;
    String x5t;
    String use;
    List<String> x5c;
    String kid;
    String alg;
    String n;

}
