package com.klopsi.user;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utils for hashing.
 *
 * @author psysiu
 */
public class HashUtils {

    /**
     * Converts byte value to hex string.
     *
     * @param hash hashed byte array
     * @return hex string
     */
    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Returns SHA-256 hash of the provided text value.
     *
     * @param original text value to be hashed
     * @return SHA-256 hash value
     */
    public static String sha256(String original) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(original.getBytes("UTF-8"));
            return String.format("%064x", new java.math.BigInteger(1, md.digest()));
//
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] encodedhash = digest.digest(original.getBytes(StandardCharsets.UTF_8));
//            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
