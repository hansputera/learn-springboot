package tech.thehanifs.testspring.encryption;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class aes256gcm {
    private final byte[] iv;
    private final SecretKey key;
    private int aes_key_size = 256;
    private int tag_length = 16;
    private int iv_length = 12;

    public aes256gcm() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(this.aes_key_size);

        // generating key
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] IV = new byte[this.iv_length];
        SecureRandom secureRandom = new SecureRandom();

        secureRandom.nextBytes(IV);

        this.iv = IV;
        this.key = secretKey;
    }

    public String encrypt(String plainText) throws Exception {
        // get cipher
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        // create secretkeyspec
        SecretKeySpec keySpec = new SecretKeySpec(this.key.getEncoded(), "AES");

        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(this.tag_length * 8, this.iv);

        // initialize cipher
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);

        // performing
        byte[] cipherText = cipher.doFinal(plainText.getBytes());

        // encode to hex
        return String.valueOf(Hex.encodeHex(cipherText));
    }

    public String decrypt(String cipherText) throws Exception {
        // decode hex to char array
        char[] hexCipher = cipherText.toCharArray();
        // get real cipher
        byte[] realCipher = Hex.decodeHex(hexCipher);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(this.key.getEncoded(), "AES");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(this.tag_length * 8, this.iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);
        byte[] decrypted = cipher.doFinal(realCipher);

        return new String(decrypted);
    }
}
