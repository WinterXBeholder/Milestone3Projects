package learn.Lesson01Exceptions;

// Unchecked Exception. Extend an unchecked exception
public class RequiredStringException extends RuntimeException {


    public RequiredStringException(String message) {
        super(message);
    }
}
