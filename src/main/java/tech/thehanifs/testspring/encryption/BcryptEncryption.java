package tech.thehanifs.testspring.encryption;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

public class BcryptEncryption {
    public static PasswordEncoder encoder = new BCryptPasswordEncoder(31, new SecureRandom());
}
