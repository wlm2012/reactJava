package com.study.reactJava.infrastructure.utils;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Base64;

public class HeFengJwtUtil {

    public static String getJwt(String privateKeyString, String kid, String sub) {
        try {
            // Private key
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = null;

            keyFactory = KeyFactory.getInstance("EdDSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            // Header
            String headerJson = "{\"alg\": \"EdDSA\", \"kid\": \"" + kid + "\"}";

            // Payload
            long iat = ZonedDateTime.now(ZoneOffset.UTC).toEpochSecond() - 30;
            long exp = iat + 900;
            String payloadJson = "{\"sub\": \"" + sub + "\", \"iat\": " + iat + ", \"exp\": " + exp + "}";

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
            return jwt;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
    }
}
