package tech.thehanifs.testspring.exception;

public class AlreadyLoginException extends RuntimeException {
    public AlreadyLoginException(String username) {
        super("You already logged in as " + username + " and, you can't access this resource.");
    }
}
