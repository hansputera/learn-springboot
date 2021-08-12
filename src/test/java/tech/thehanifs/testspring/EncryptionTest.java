package tech.thehanifs.testspring;

import tech.thehanifs.testspring.encryption.Aes256GcmEncryption;

public class EncryptionTest
{
    public static void main(String[] args) throws Exception {
        String myText = "Hello world";

        Aes256GcmEncryption aes256enc = new Aes256GcmEncryption();
        String hasil = aes256enc.encrypt(myText);
        System.out.println(hasil);
        System.out.println(aes256enc.decrypt(hasil));
    }
}
