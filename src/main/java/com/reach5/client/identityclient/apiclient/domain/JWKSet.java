package com.reach5.client.identityclient.apiclient.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JWKSet {

    List<JWKKey> keys;
}
