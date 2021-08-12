package tech.thehanifs.testspring.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.thehanifs.testspring.encryption.BcryptEncryption;
import tech.thehanifs.testspring.users.User;
import tech.thehanifs.testspring.users.UserRepository;

import java.util.Locale;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger("User Service");
    @Autowired
    UserRepository userRepository;

    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    public boolean addUser(String name, String password, String email, String username, int role) {
        if (this.userRepository.findByUsername(username) != null || this.userRepository.findByEmail(email) != null) {
            return false;
        } else {
            if (role < 1 || role > 2) return false;
            User user = new User(name, role, username.toLowerCase(), BcryptEncryption.encoder.encode(password), email.toLowerCase());
            userRepository.save(user);
            return true;
        }
    }
}
