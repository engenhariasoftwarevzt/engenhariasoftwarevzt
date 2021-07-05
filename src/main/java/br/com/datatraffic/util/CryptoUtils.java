package br.com.datatraffic.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilitária para Criptografia.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CryptoUtils {

    public static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuilder buffer = new StringBuilder();
        for (byte arrayByte : arrayBytes) {
            buffer.append(Integer.toString((arrayByte & 0xff) + 0x100, 16).substring(1));
        }
        return buffer.toString();
    }

    public static byte[] calcularHashSHA256(byte[] pack) {
        byte[] hashGerado = null;
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            hashGerado = algorithm.digest(pack);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not generate hash from Object", e);
        }
        return hashGerado;

    }

}
