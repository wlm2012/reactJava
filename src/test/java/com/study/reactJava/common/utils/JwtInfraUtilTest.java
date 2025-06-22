package com.study.reactJava.common.utils;

import com.study.reactJava.infrastructure.utils.JwtInfraUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Base64;

@SpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class JwtInfraUtilTest {

    private final JwtInfraUtil jwtInfraUtil;

    @Test
    void createToken() {
        String token = jwtInfraUtil.createToken("1234");
        System.out.println("token = " + token);
    }

    @Test
    void test_fenghe() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        // Private key
        String privateKeyString = "MC4CAQAwBQYDK2VwBCIEIDoHJhTe17hUBF5yaG6lFrrP2/G5tmICKhVJWIuihTSf";
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EdDSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        // Header
        String headerJson = "{\"alg\": \"EdDSA\", \"kid\": \"K7B7YR3UW3\"}";

        // Payload
        long iat = ZonedDateTime.now(ZoneOffset.UTC).toEpochSecond() - 30;
        long exp = iat + 900;
        String payloadJson = "{\"sub\": \"278AWW59M5\", \"iat\": " + iat + ", \"exp\": " + exp + "}";

        // Base64url header+payload
        String headerEncoded = Base64.getUrlEncoder().encodeToString(headerJson.getBytes(StandardCharsets.UTF_8));
        String payloadEncoded = Base64.getUrlEncoder().encodeToString(payloadJson.getBytes(StandardCharsets.UTF_8));
        String data = headerEncoded + "." + payloadEncoded;

        // Sign
        Signature signer = Signature.getInstance("EdDSA");
        signer.initSign(privateKey);
        signer.update(data.getBytes(StandardCharsets.UTF_8));
        byte[] signature = signer.sign();

        String signatureEncoded = Base64.getUrlEncoder().encodeToString(signature);

        String jwt = data + "." + signatureEncoded;

        // Print Token
        System.out.println("Signature:\n" + signatureEncoded);
        System.out.println("JWT:\n" + jwt);

    }

}