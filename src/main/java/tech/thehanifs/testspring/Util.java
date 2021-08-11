package tech.thehanifs.testspring;

import tech.thehanifs.testspring.encryption.aes256gcm;

import java.security.NoSuchAlgorithmException;

public class Util {
    public static aes256gcm encryption_aes256gcm;
    static {
        try {
            encryption_aes256gcm = new aes256gcm();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
