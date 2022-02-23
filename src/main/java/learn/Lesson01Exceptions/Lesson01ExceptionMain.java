package learn.Lesson01Exceptions;

import learn.helpers.Input;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Scanner;

public class Lesson01ExceptionMain {

    public static void main() {

        /* // BAD CODE:
        String value = null;
        int[] numbers = new int[0];

        // 1. java.lang.NullPointerException
        int length = value.length();

        // 2. java.lang.ArrayIndexOutOfBoundsException
        int element = numbers[1];

        // 3. java.lang.NumberFormatException
        int n = Integer.parseInt(value);


        try {
            // code that can cause an exception
        } catch ([exception type] [variable name]) {
            // This code only runs if the exception occurs.
        }*/

        String input = null;
        int value = 0;
        try {
            value = Integer.parseInt(input);
            System.out.println("Your value is: " + value); // doesn't execute
        } catch (NumberFormatException ex) {
            System.out.println("I acknowledge I may receive a NumberFormatException.");
            System.out.println("It's okay. I have a plan.");
            System.out.println(ex);
        }
        input = "this is definitely not a number";
        try {
            value = Integer.parseInt(input);
            System.out.println("Your value is: " + value); // doesn't execute
        } catch (NumberFormatException ex) {
            System.out.println("I acknowledge I may receive a NumberFormatException.");
            System.out.println("I am not defeated. There's still hope.");
            System.out.println(ex);
        }
        input = "27";
        try {
            value = Integer.parseInt(input);
            System.out.println("Your value is: " + value); // executes
        } catch (NumberFormatException ex) {
            System.out.println("I acknowledge I may receive a NumberFormatException.");
            System.out.println("Hmmmm. Not sure what to do if this fails...");
            System.out.println(ex);
        }

        // `values` could be null or contain fewer than 6 elements.
        String[] values = {"a", "b"}; //= getValues();
        String result = null;
        try {
            result = values[5];
            // `result` could be null
            System.out.println(result.length());
        } catch (NullPointerException ex) {
            System.out.println("Either `values` or `result` is null.");
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("There are fewer than 6 elements in the array.");
        }

        Scanner console = new Scanner(System.in);
        // We don't know what we'll get here.
        input = Input.getWord(console);
        int intResult = 0;
        try {
            intResult = Integer.parseInt(input);
            System.out.println("Result is: " + intResult);
        } catch (NumberFormatException ex) {
            System.out.println("The input isn't a number.");
        } catch (RuntimeException ex) {
            System.out.println("This will trigger if we don't receive an NumberFormatException");
            System.out.println("but we do receive a RuntimeException.");
        } catch (Exception ex) {
            System.out.println("This will trigger if we don't receive an RuntimeException");
            System.out.println("but we do receive a Exception.");
        } catch (Throwable throwable) {
            System.out.println("This will trigger if we don't receive an Exception");
            System.out.println("but we do receive a Throwable.");
        } /*
        Be careful. The above is for illustration only. We never want to catch exceptions that we can't handle
        meaningfully. Generic exceptions like Throwable, Exception, or RuntimeException should (almost) never be caught.
        That's a case where we didn't anticipate a specific exception, so it's better to let the program crash than to
        risk continuing without understanding what went wrong. */


        // `values` could be null or contain fewer than 6 elements.
        String strResult = null;
        try {
            strResult = values[5];
            // `strResult` could be null
            System.out.println(strResult.length());
        } catch (NullPointerException | IndexOutOfBoundsException ex) { //can use | or operator for multiple exceptions
            System.out.println("Both the NullPointerException and IndexOutOfBoundsException are handled the same.");
            System.out.println(ex);
        }


        // using Finally which will run regardless of try success or failure:
        FileReader reader = null;
        try {
            reader = new FileReader("data.txt");
            System.out.println(reader.read());
        } catch (FileNotFoundException ex) {
            System.out.println("Couldn't find the file.");
        } catch (IOException ex) {
            System.out.println("Couldn't read from the file.");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    System.out.println("Couldn't close the file.");
                }
            }
        }


        RequiredStringException uncheckedException = new RequiredStringException(
                "This exception is not checked until runtime");
        NegativeValueException checkedException = new NegativeValueException(
                "this exception is checked at compile time, before the program is allowed to run");


    }

    // Methods to demonstrate 'throws', a way to pass the checked exceptions up to whatever method calls this method,
    // without handling the exception here.
    public void getMinAndMax() throws ParseException { // acknowledged, not handled
        Number min = readNumber("Enter a minimum: ");
        Number max = readNumber("Enter a maximum: ");}
    public Number readNumber(String prompt) throws ParseException { // acknowledged, not handled
        Scanner console = new Scanner(System.in);
        NumberFormat formatter = NumberFormat.getInstance();
        System.out.print(prompt);
        return formatter.parse(console.nextLine());}
    // you would handle this thrown exception by putting getMinAndMax() into a try catch block


    /* Throwing An Exception
        To throw an exception when something is wrong:

        1. Detect the error condition using decision statements.
        2. Instantiate an appropriate exception instance with appropriate messages and state.
        3. Use the throw keyword followed by the exception instance. The throw keyword immediately terminates a method.
                No value is returned. To avoid a crash, the method caller must handle the exception.
        4. If the exception is checked, add the throws clause to the method. */

    // Custom checked exception
    public int addPositiveValues(int a, int b) throws NegativeValueException { // 4. Acknowledge the checked exception.
        // 1. Detect problem.
        if (a < 0 || b < 0) {
            // 2. Instantiate exception.
            NegativeValueException ex = new NegativeValueException("Arguments may not be negative.");
            // 3. Throw. Immediately terminates the method. No value is returned.
            throw ex;
        }
        return a + b;
    }

    // Custom unchecked exception
    public String addDr(String name) {
        // 1. Detect problem.
        if (name == null || name.isEmpty()) {
            // 2. Instantiate exception.
            RequiredStringException ex = new RequiredStringException("`name` is required.");
            // 3. Throw. Immediately terminates the method. No value is returned.
            throw ex;
        }
        return "Dr. " + name;
    }

    // There are a lot of good existing exceptions. exception from the standard library:
    public String repeat(String value, int times) {
        // 1. Detect problem.
        if (times < 0) {
            // 2. Instantiate exception.
            IllegalArgumentException ex = new IllegalArgumentException("`times` cannot be negative.");
            // 3. Throw. Immediately terminates the method. No value is returned.
            throw ex;
        }
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < times; i++) {
            buffer.append(value);
        }
        return buffer.toString();
    }
}

