package learn.Lesson05Repository.memories.data;

import learn.Lesson04Layers.memories.models.Memory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemoryFileRepositoryTest {
    /*The @BeforeEach annotation tells JUnit to run the setupTest method before each test method runs. Inside, we copy
    the content of the memories-seed.txt file into the memories-test.txt file. This ensures each test has the data it
    needs to verify an accurate result.
     */

    static final String SEED_FILE_PATH = "./data/memories-seed.txt";
    static final String TEST_FILE_PATH = "./data/memories-test.txt";

    MemoryFileRepository repository = new MemoryFileRepository(TEST_FILE_PATH);

    @BeforeEach
    void setupTest() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    /* Any operation that reads from the test file can be confirmed by counting records or an existence check. It's
    also possible to confirm values for specific records and fields. Testing has a sweet spot. It's always possible to
    write more tests and more elaborate tests, but we have to balance the return on investment. Here, we choose to
    confirm fields in the findById test and we count records in the findAll and findShareable tests.
     */
    @Test
    void findAll() throws DataAccessException {
        List<Memory> actual = repository.findAll();
        assertEquals(3, actual.size());
    }

    @Test
    void findShareable() throws DataAccessException {
        List<Memory> actual = repository.findShareable(true);
        assertEquals(2, actual.size()); // seed data has 2 shareable memories

        actual = repository.findShareable(false);
        assertEquals(1, actual.size()); // 1 non-shareable memory
    }

    @Test
    void findById() throws DataAccessException {
        Memory memory = repository.findById(2);
        assertNotNull(memory);
        assertEquals("Uncle Sherwin", memory.getFrom());
        assertTrue(memory.isShareable());

        memory = repository.findById(1024);
        assertNull(memory); // id 1024 does not exist, expect null
    }


    /*To fully confirm a create operation, we must also read. There's no way to guarantee the record was saved without
    pulling it back out again.
    In tests that alter data, it's important to think about the three testing A's: Arrange, Act, and Assert. All of our
    tests are pre-arranged to a known good state with setupTest. Below, we arrange a memory to be inserted, act with the
    add and findAll methods, and perform several assertions
     */
    @Test
    void add() throws DataAccessException {
        Memory memory = new Memory();
        memory.setFrom("A Friend");
        memory.setContent("A special memory.");

        Memory actual = repository.add(memory);
        assertEquals(4, actual.getId());

        List<Memory> all = repository.findAll();
        assertEquals(4, all.size());

        actual = all.get(3);                        // the newly-added memory
        assertEquals(4, actual.getId());
        assertEquals("A Friend", actual.getFrom()); // confirm all fields
        assertEquals("A special memory.", actual.getContent());
        assertFalse(actual.isShareable());
    }


    /*
    Here, we fetch an existing memory from the repository, update its fields, save it, and pull it back out again to
    confirm the updates worked. We aren't required to fetch an existing memory. If we instantiated a Memory and assigned
     it an id, it would update any existing memory with that id.
    Often, we want to confirm negative results as well as positive results. Our test confirms both that existing
    memories can be updated and memories that don't exist cannot.
     */
    @Test
    void update() throws DataAccessException {
        Memory memory = repository.findById(2);
        memory.setFrom("Sherwin");                    // was Uncle Sherwin
        memory.setShareable(false);                   // was true
        assertTrue(repository.update(memory));

        memory = repository.findById(2);
        assertNotNull(memory);                        // confirm the memory exists
        assertEquals("Sherwin", memory.getFrom());    // confirm the memory was updated
        assertFalse(memory.isShareable());

        Memory doesNotExist = new Memory();
        doesNotExist.setId(1024);
        assertFalse(repository.update(doesNotExist)); // can't update a memory that doesn't exist
    }


    /* The deleteById method returns a success status as a boolean. Just to be sure, we check that a successful delete
    reduced the number of records by one.
     */
    @Test
    void deleteById() throws DataAccessException {
        int count = repository.findAll().size();
        assertTrue(repository.deleteById(1));
        assertFalse(repository.deleteById(1024));
        assertEquals(count - 1, repository.findAll().size());
    }
}