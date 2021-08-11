package tech.thehanifs.testspring.users;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.web.bind.annotation.*;
import tech.thehanifs.testspring.exception.AccessDeniedException;
import tech.thehanifs.testspring.exception.AlreadyLoginException;
import tech.thehanifs.testspring.exception.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@EnableJdbcHttpSession
@RestController
public class UserController {
    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
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
    User postUser(HttpServletRequest req, @RequestBody User newUser) {
        HttpSession session = req.getSession();
        User user_session = (User) session.getAttribute("user");
        if (user_session != null) throw new AlreadyLoginException(user_session.getUsername());
        return repository.save(newUser);
    }

    @GetMapping("/users/{id}")
    EntityModel<User> getUser(HttpServletRequest req, @PathVariable Long id) {
        HttpSession session = req.getSession();
        User user_session = (User) session.getAttribute("user");
        if (user_session == null) throw new AccessDeniedException();
        else if (user_session.getRole() == 2 && id.equals(user_session.getId()) == false) throw new AccessDeniedException();
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return EntityModel.of(user, linkTo(methodOn(UserController.class).getUser(req, user.getId())).withSelfRel(), linkTo(methodOn(UserController.class).all(req)).withRel("users"));
    }

    @GetMapping("/logout")
    Boolean logoutUser(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user_session = (User) session.getAttribute("user");
        if (user_session == null) return false;
        else {
            return true;
        }
    }
}
