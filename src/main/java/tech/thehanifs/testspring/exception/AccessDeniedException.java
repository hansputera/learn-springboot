package tech.thehanifs.testspring.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super("You're not allowed to view this resource.");
    }
}
