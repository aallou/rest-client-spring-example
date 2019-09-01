package com.reach5.client.identityclient.service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.reach5.client.identityclient.apiclient.domain.JWKKey;
import com.reach5.client.identityclient.apiclient.domain.JWKSet;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class JwksVerifier {

    public static JWTClaimsSet validateJwts(String jwksUrl, String jwtToken) throws Exception {

        JWKSource keySource = new RemoteJWKSet(new URL(jwksUrl));
        JWSAlgorithm expectedJWSAlg = JWSAlgorithm.RS256;
        JWSKeySelector keySelector = new JWSVerificationKeySelector(expectedJWSAlg, keySource);
        ConfigurableJWTProcessor jwtProcessor = new DefaultJWTProcessor();
        jwtProcessor.setJWSKeySelector(keySelector);

        return jwtProcessor.process(jwtToken, null);
    }

    public static Jwt validateJwts2(String jwksUrl, String jwtToken) throws NoSuchAlgorithmException, InvalidKeySpecException, CertificateException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JWKSet> jwkSetEntity = restTemplate.getForEntity(jwksUrl, JWKSet.class);
        JWKKey key = jwkSetEntity.getBody().getKeys().get(0);
        BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(key.getN()));
        BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(key.getE()));
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(modulus, exponent));

        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) factory
                .generateCertificate(new ByteArrayInputStream(
                        DatatypeConverter.parseBase64Binary(key.getX5c().get(0))));
        PublicKey publicKey2 = cert.getPublicKey();

        // publicKey == publicKey

        return Jwts.parser().setSigningKey(publicKey).parse(jwtToken);
    }
}
