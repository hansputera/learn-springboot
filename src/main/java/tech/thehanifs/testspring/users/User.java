package tech.thehanifs.testspring.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.util.Objects;

@Entity
public class User {
    private @Id @GeneratedValue Long id;

    @NotNull
    @NotEmpty
    @NotBlank(message = "Name is required")
    private String name;

    @Column()
    private int role;

    @NotNull
    @NotEmpty
    @NotBlank(message = "username is required")
    private String username;

    @NotNull
    @NotEmpty
    @NotBlank(message = "password is required")
    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W\\_])[A-Za-z\\d\\W\\_]{8,}$")
    private String password;

    @NotNull
    @NotEmpty
    @NotBlank(message = "email is required")
    @Email
    private String email;

    // role: 1 for admin 2 for student
    public User() {}
    public User(String name, int role, String username, String password, String email) {
        this.name = name;
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public int getRole() {
        return this.role;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public String getEmail() {
        return this.email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(int RoleID) {
        this.role = RoleID;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof User)) {
            return false;
        } else {
            User user = (User) obj;
            return Objects.equals(this.id, user.id) && Objects.equals(this.name, user.name) && Objects.equals(this.role, user.role);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.role);
    }

}
