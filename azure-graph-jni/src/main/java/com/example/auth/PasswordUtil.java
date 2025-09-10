package com.example.auth;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class PasswordUtil {
private static final int ITERATIONS = 10000;
private static final int KEY_LENGTH = 256; // bits


public static String hashPassword(char[] password) {
try {
byte[] salt = new byte[16];
SecureRandom sr = SecureRandom.getInstanceStrong();
sr.nextBytes(salt);


PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
byte[] hash = skf.generateSecret(spec).getEncoded();

return ITERATIONS + ":" + Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
throw new RuntimeException(e);
}
}


public static boolean verifyPassword(char[] password, String stored) {
try {
String[] parts = stored.split(":");
int iterations = Integer.parseInt(parts[0]);
byte[] salt = Base64.getDecoder().decode(parts[1]);
byte[] hash = Base64.getDecoder().decode(parts[2]);


PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, hash.length * 8);
SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
byte[] testHash = skf.generateSecret(spec).getEncoded();


if (testHash.length != hash.length) return false;
int diff = 0;
for (int i = 0; i < hash.length; i++) diff |= hash[i] ^ testHash[i];
return diff == 0;
} catch (Exception e) {
return false;
}
}

}
