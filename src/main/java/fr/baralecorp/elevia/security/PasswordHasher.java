package fr.baralecorp.elevia.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PasswordHasher {

    @Value( "${security.salt:sel}" )
    private String salt = "sel";

    private MessageDigest digest;

    public PasswordHasher() throws NoSuchAlgorithmException {
         digest = MessageDigest.getInstance("SHA-256");
         digest.update(salt.getBytes(StandardCharsets.UTF_8));
    }

    public String hash(@NotNull String password){
        return bytesToHex(digest.digest(password.getBytes(StandardCharsets.UTF_8)));
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
