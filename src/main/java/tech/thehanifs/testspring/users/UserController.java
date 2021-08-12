package tech.thehanifs.testspring.users;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tech.thehanifs.testspring.exception.AccessDeniedException;
import tech.thehanifs.testspring.exception.AlreadyLoginException;
import tech.thehanifs.testspring.exception.UserNotFoundException;
import tech.thehanifs.testspring.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@EnableJdbcHttpSession
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserRepository repository;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger("User Controller");

    UserController(UserRepository repository) {
        this.repository = repository;
        this.userService = new UserService(repository);
    }

    @GetMapping("/users")
    CollectionModel<EntityModel<User>> all(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user_session = (User) session.getAttribute("user");
        if (user_session == null) throw new AccessDeniedException();
        else if (user_session.getRole() == 2) throw new AccessDeniedException();
        List<EntityModel<User>> users = repository.findAll().stream().map(user -> EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUser(req, user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).all(req)).withRel("users")
                )).collect(Collectors.toList());
        return CollectionModel.of(users, linkTo(methodOn(UserController.class).all(req)).withSelfRel());
    }

    @PostMapping("/users")
    String postUser(HttpServletRequest req, @Valid @RequestBody User newUser, Errors error) {
        HttpSession session = req.getSession();
        User user_session = (User) session.getAttribute("user");
        if (user_session != null) throw new AlreadyLoginException(user_session.getUsername());
        if (error.hasErrors()) {
            logger.debug(String.valueOf(error.getFieldError()));
            return "Invalid body";
        }
        if (this.repository.findByUsername(newUser.getUsername()) != null || this.repository.findByEmail(newUser.getEmail()) != null) {
            return "Your requesting data already registered";
        }
        try {
            boolean successCreated = userService.addUser(
                    newUser.getName(),
                    newUser.getPassword(),
                    newUser.getEmail(),
                    newUser.getUsername(),
                    2
            );
            if (successCreated) return "Successfuly registered";
            else return "Fail to register";
        } catch (Exception err) {
            logger.error(err.getMessage());
            return "Something was wrong";
        }
    }

    @GetMapping("/users/{id}")
    EntityModel<User> getUser(HttpServletRequest req, @PathVariable Long id) {
        HttpSession session = req.getSession();
        User user_session = (User) session.getAttribute("user");
        if (user_session == null) throw new AccessDeniedException();
        else if (user_session.getRole() == 2 && !id.equals(user_session.getId())) throw new AccessDeniedException();
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return EntityModel.of(user, linkTo(methodOn(UserController.class).getUser(req, user.getId())).withSelfRel(), linkTo(methodOn(UserController.class).all(req)).withRel("users"));
    }

    @GetMapping("/logout")
    Boolean logoutUser(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user_session = (User) session.getAttribute("user");
        if (user_session == null) return false;
        else {
            session.invalidate();
            return true;
        }
    }
}
