package packages.Lesson01Exceptions;

// Checked Exception. Extend a checked exception
public class NegativeValueException extends Exception {

    public NegativeValueException(String message) {
        super(message);
    }

}
