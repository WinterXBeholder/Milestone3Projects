package learn.Lesson03FileIO;

// two kinds of io packages:
//import java.io
//import java.nio


// using File:
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;

// using Files:
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Lesson03FileIOMain {
    public static void main() {
        File file = new File("colors.txt");
        try{
            if (file.createNewFile()) {
                System.out.println("colors.txt created.");
            } else {
                System.out.println("colors.txt already exists.");
            }
            // a catch here is required because IOEXception is a checked exception
        } catch (IOException ex) {
            ex.printStackTrace();
            /* In this case, we print the exception's stack trace to the standard output. In larger applications,
            this isn't acceptable.*/
        }

        // Absolute file paths:
        /*
        Windows
            C:\Users\jany\Desktop\file.ext
        Mac or Linux
            /Users/jany/Desktop/file.ext
         */
        // relative file paths would be only part of these:
        /*
        Windows
            C:\Users\jany\Documents\repos\fileio-project\colors.txt
        Mac or Linux
            /Users/jany/Documents/repos/fileio-project/colors.txt
         */



        PrintWriter writer = null;
        /* The PrintWriter class automatically creates a file if it doesn't exist as long as the program has permission
        to do so. It includes many of the same methods used in System.out: print, println, and printf. */
        try {
            writer = new PrintWriter("colors.txt");
            writer.println("red");
            writer.println("blue");
            writer.println("yellow");
        } catch(FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            //always clean up!
            if (writer!=null) {
                writer.close();
                /* The JRE will clean up I/O instances automatically with garbage collection, but not in a timely
                fashion. If an instance isn't closed, it will prevent other methods and processes from accessing the
                file, which will lead to run-time exceptions. Always close I/O resources. */
            }
        }


        /* A try-with-resources statement uses the try keyword, defines the closable resource inside parentheses, and automatically closes the resource when the statement is complete.
            There is no downside to try-with-resources, only upsides:
            guarantees resource clean up
            less code
            less complicated code which means we're less likely to make a mistake */
        try (PrintWriter writer2 = new PrintWriter("colors.txt")) {
            writer2.println("red");
            writer2.println("blue");
            writer2.println("yellow");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } // If we run the code above, again and again, we never add to the file. Instead, the file is overwritten.

        // PrintWriter class doesn't support appending on its own, so we must add a second class, FileWriter
        // The second FileWriter constructor argument, true, indicates this is an appending write.
        try (FileWriter fileWriter = new FileWriter("colors.txt", true);
                PrintWriter writer1 = new PrintWriter(fileWriter)) {
            // above, we can let try-with manage multiple resources using semicolon
            writer.println("green");
            writer.println("orange");
            writer.println("purple");
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        /* The BufferedReader class provides the ability to read a line at a time similar to the Scanner class. Other
        readers use byte-oriented reading, so the BufferedReader is a good choice when we're using text-based, line-
        based file processing.
        BufferedReader constructors don't accept file paths. Instead, they accept a Reader.*/
        System.out.println("File contents:");
        try (FileReader fileReader = new FileReader("colors.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {

            // When there are no more lines, readline() returns null.
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                System.out.println(line);
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }


        file = new File("colors.txt");
        boolean success = file.delete();
        if (success) {
            System.out.println("colors.txt was deleted");
        } else {
            System.out.println("colors.txt NOT deleted");
        }




        // Files class is different from File class
        /* Files must use a Path instance instead of file path strings.
           There's no need to manage the underlying I/O resource. Files cleans up for you.
           Individual lines must be managed with an iterable collection or pre-formatted as a single string. All write
              methods open, write, and close. There's no way to call multiple writes on a single resource instance. */

        ArrayList<String> lines = new ArrayList<>();
        lines.add("hydrogen");
        lines.add("helium");
        lines.add("lithium");

        // create a file and write to it
        Path path = Paths.get("elements.txt");
        try {
            Files.write(path, lines, StandardOpenOption.CREATE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        lines.clear();
        lines.add("beryllium");
        lines.add("boron");
        lines.add("carbon");

        //append to a file:
        try {
            Files.write(path, lines, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //read from the file:
        try{
            for (String line : Files.readAllLines(path)) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //delete the file:
        try {
            success = Files.deleteIfExists(path);
            if (success) {
                System.out.println("elements.txt was deleted");
            } else {
                System.out.println("elements.txt was NOT deleted");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        // File class can also be used for directories. Below we can list a directory's contents as file or sub-direcotry
        File workingDirectory = new File(".");
        for (String path1 : workingDirectory.list()) {
            File entry = new File(path1);
            if (entry.isDirectory()) {
                System.out.printf("Directory: ");
            } else {
                System.out.printf("File     : ");
            }
            System.out.printf("%s%n", entry.getName());
        }
    }



































}
