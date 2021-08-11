package tech.thehanifs.testspring.annotations;

import tech.thehanifs.testspring.validations.PasswordValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

public class PasswordAnnotation {
    @Documented
    @Constraint(validatedBy = PasswordValidation.class)
    @Target({
            ElementType.METHOD, ElementType.FIELD
    })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PasswordConstraint {
        String message() default "Invalid password";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }
}
