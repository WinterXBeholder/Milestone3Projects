package learn.memories.ui;

import java.util.Scanner;

public class ConsoleIO implements TextIO {

    /* ConsoleIO contains console input and output methods that are not aware of the domain. Their scope is small and
    they're granular. They can be used to build more complicated user interfaces.
    Officially, only a view is required for the MVC pattern, but adding a class like ConsoleIO confers advantages.
    Arguably, raw console I/O is a separate concern from domain-specific I/O. It's easier to think about it separately.
    Since ConsoleIO isn't aware of models like Memory, it can be used in any CLI project. It's reusable.     */

    private final Scanner console = new Scanner(System.in);

    @Override
    public void println(Object value) {
        System.out.println(value);
    }

    @Override
    public void print(Object value) {
        System.out.print(value);
    }

    @Override
    public void printf(String format, Object... values) {
        // Object... is a java vararg. It can account for any number of additional arguments.
        System.out.printf(format, values);
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        return console.nextLine();
    }

    @Override
    public boolean readBoolean(String prompt) {
        String result = readString(prompt);
        return result.equalsIgnoreCase("y");
    }

    @Override
    public int readInt(String prompt) {
        while (true) {
            String value = readString(prompt);
            try {
                int result = Integer.parseInt(value);
                return result;
            } catch (NumberFormatException ex) {
                printf("'%s' is not a valid number.%n", value);
            }
        }
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            printf("Value must be between %s and %s.%n", min, max);
        }
    }
}