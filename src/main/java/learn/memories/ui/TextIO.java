package learn.memories.ui;

public interface TextIO {

    /* TextIO is an interface for ConsoleIO. Design-wise, it may seem like overkill to add an interface to the small
    ConsoleIO class. But when we inject an interface as a View dependency, we can swap concrete implementations. That
    opens the possibility to test the View or the Controller in the future. */

    void println(Object value);

    void print(Object value);

    void printf(String format, Object... values);

    String readString(String prompt);

    boolean readBoolean(String prompt);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);
}