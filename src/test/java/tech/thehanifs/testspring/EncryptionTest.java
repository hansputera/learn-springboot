package tech.thehanifs.testspring;

import tech.thehanifs.testspring.encryption.aes256gcm;

public class EncryptionTest
{
    public static void main(String[] args) throws Exception {
        String myText = "Hello world";

        aes256gcm aes256enc = new aes256gcm();
        String hasil = aes256enc.encrypt(myText);
        System.out.println(hasil);
        System.out.println(aes256enc.decrypt(hasil));
    }
}
