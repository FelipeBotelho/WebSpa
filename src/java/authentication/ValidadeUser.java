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
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author lucas
 */
public class ValidadeUser {

    public boolean ValidateToken(String jweString, SecretKey secretKey) {
        try {
            // Parse the JWE string
            JWEObject jweObject = JWEObject.parse(jweString);

            // Decrypt with shared key
            jweObject.decrypt(new DirectDecrypter(secretKey.getEncoded()));

            // Extract payload
            SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();

            if (signedJWT == null) {
                return false;
            }
            // Check the HMAC
            if (!signedJWT.verify(new MACVerifier(secretKey.getEncoded()))) {
                return false;
            }

            if (signedJWT.getJWTClaimsSet().getExpirationTime().compareTo(new java.util.Date()) < 0) {
                return false;
            }           
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String GenerateToken(String subject, SecretKey secretKey, Map<String, Object> claims) {
        try {
            // Create HMAC signer
            JWSSigner signer = new MACSigner(secretKey.getEncoded());

// Prepare JWT with claims set
            JWTClaimsSet claimsSet = new JWTClaimsSet();
            claimsSet.setSubject(subject);

            java.util.Calendar expiration = Calendar.getInstance();
            expiration.add(Calendar.MINUTE, 15);
            claimsSet.setExpirationTime(expiration.getTime());

            claimsSet.setCustomClaims(claims);
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
