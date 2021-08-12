package tech.thehanifs.testspring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.thehanifs.testspring.classes.LoginPayloadClass;
import tech.thehanifs.testspring.encryption.BcryptEncryption;
import tech.thehanifs.testspring.users.User;
import tech.thehanifs.testspring.users.UserRepository;

@Service
public class UserService {
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

    public boolean isValidPayload(LoginPayloadClass payload) {
        if (payload.username == null && payload.email == null && payload.password == null) return false;
        else if (payload.username != null && payload.email == null) return false;
        else {
            var user = userRepository.findByUsername(payload.username);
            if (user == null) user = userRepository.findByEmail(payload.email);

            if (user == null) return false;
            else return BcryptEncryption.encoder.matches(payload.password, user.getPassword());
        }
    }
}
