package tech.thehanifs.testspring.encryption;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BcryptEncryption {
    public static PasswordEncoder encoder = new BCryptPasswordEncoder();
}
