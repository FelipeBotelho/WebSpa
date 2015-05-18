/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;
import java.time.Instant;
import java.util.Date;

/**
 *
 * @author lucas
 */
public class AuthTest {

    public String Test(SecretKey secretKey) {
        try {
            // Generate 256-bit AES key for HMAC as well as encryption
            //KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            //keyGen.init(256);
            //secretKey = keyGen.generateKey();

// Create HMAC signer
            JWSSigner signer = new MACSigner(secretKey.getEncoded());

// Prepare JWT with claims set
            JWTClaimsSet claimsSet = new JWTClaimsSet();
            claimsSet.setSubject("alice");
            claimsSet.setExpirationTime(new Date());
            claimsSet.setIssueTime(new java.util.Date());
            claimsSet.setIssuer("https://c2id.com");

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

// Apply the HMAC
            signedJWT.sign(signer);

// Create JWE object with signed JWT as payload
            JWEObject jweObject = new JWEObject(
                    new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A256GCM).contentType("JWT").build(),
                    new Payload(signedJWT));

// Perform encryption
            jweObject.encrypt(new DirectEncrypter(secretKey.getEncoded()));

// Serialise to JWE compact form
            String jweString = jweObject.serialize();
            return jweObject.serialize();
        } catch (Exception e) {
            return e.toString();
        }
    }
}
