package tech.thehanifs.testspring.classes;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginPayloadClass {
    @Column(nullable = true)
    public String username;

    @NotNull
    @NotBlank
    @NotEmpty
    public String password;

    @Column(nullable = true)
    public String email;
}
