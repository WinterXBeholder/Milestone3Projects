package learn.memories;

import learn.memories.data.MemoryFileRepository;
import learn.memories.domain.MemoryService;
import learn.memories.ui.ConsoleIO;
import learn.memories.ui.Controller;
import learn.memories.ui.View;

public class App {

    /* Working backward from Controller:
    Controller(ui) requires: View(ui), MemoryService(domain)
    View(ui) requires: TextIO(ui)
    MemoryService(domain) requires: MemoryRepository(data)
    The App class knits classes together from each layer. It's aware of all data types except for test implementations.
    When an interface is required, App must supply a concrete implementation. The TextIO dependency is satisfied with
    the ConsoleIO class. The MemoryRepository dependency is satisfied with the MemoryFileRepository class. */

    public static void main(String[] args) {
        // "./data/memories.txt" is the path to our "production" data file.
        MemoryFileRepository repository = new MemoryFileRepository("./data/memories.txt");
        // repository satisfies the MemoryRepository dependency.
        MemoryService service = new MemoryService(repository);

        ConsoleIO io = new ConsoleIO();
        // io satisfies the TextIO dependency.
        View view = new View(io);

        // view satisfies the View dependency
        // service satisfies the MemoryService dependency
        Controller controller = new Controller(view, service);

        // Run the app!
        controller.run();
    }

}