package tech.thehanifs.testspring;

import tech.thehanifs.testspring.encryption.Aes256GcmEncryption;

import java.security.NoSuchAlgorithmException;


public class Util {
    public static Aes256GcmEncryption encryption_aes256GcmEncryption;

    static {
        try {
            encryption_aes256GcmEncryption = new Aes256GcmEncryption();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
