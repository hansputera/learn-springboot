package tech.thehanifs.testspring.validations;

import tech.thehanifs.testspring.annotations.PasswordAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidation implements ConstraintValidator<PasswordAnnotation.PasswordConstraint, String> {
    @Override
    public void initialize(PasswordAnnotation.PasswordConstraint password) {}

    @Override
    public boolean isValid(String passwordField, ConstraintValidatorContext cxt) {
        boolean passwordMatch = passwordField.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W\\_])[A-Za-z\\d\\W\\_]{8,}$");
        return passwordMatch;
    }
}
