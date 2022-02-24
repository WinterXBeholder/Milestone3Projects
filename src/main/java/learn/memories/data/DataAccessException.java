package learn.memories.data;

public class DataAccessException extends Exception {
        /* A primary goal of the data access layer is hiding the underlying technology. Our users (other developers,
        primarily in the domain layer) don't want or need to know what is being used under the hood. Since the
        underlying technologies focus on data input and output -- on a network, in a database, or in a file -- they are
        rife with checked exceptions. We shouldn't allow those specific exceptions to "leak" out of the data access
        layer. Instead, we create a custom exception to generalize the specifics. This greatly simplifies the exception
        landscape for developers who consume our data access components. */
    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}