package learn.memories.domain;

import learn.memories.data.DataAccessException;
import learn.memories.data.MemoryRepository;
import learn.memories.models.Memory;

import java.util.List;

public class MemoryService {

    /*
    There are at least three steps involved in adding a Memory to a data source:
        Validate the memory. If it's not valid, reject the operation and provide feedback with error messages.
        If it's valid, use a repository to save the memory.
        If the repository saves successfully, return the memory with its new identifier.
     */

    private final MemoryRepository repository;

    public MemoryService(MemoryRepository repository) {
        this.repository = repository;
    }

    public List<Memory> findPublicMemories() throws DataAccessException {
        return repository.findShareable(true);
    }

    public List<Memory> findPrivateMemories() throws DataAccessException {
        return repository.findShareable(false);
    }

    public MemoryResult add(Memory memory) throws DataAccessException {
        /* If the memory is valid, it's saved using the repository and added to the MemoryResult. The service's consumer
         as the option to use it. If the memory is invalid, specific error messages are added to the MemoryResult and
         the result is returned without saving. Again, the service's consumer has the option to use the failed result. */
        MemoryResult result = validate(memory);

        if (memory.getId() > 0) {
            result.addErrorMessage("Memory `id` should not be set.");
        }

        if (result.isSuccess()) {
            memory = repository.add(memory);
            result.setMemory(memory);
        }
        return result;
    }

    public MemoryResult update(Memory memory) throws DataAccessException {
        MemoryResult result = validate(memory);

        if (memory.getId() <= 0) {
            result.addErrorMessage("Memory `id` is required.");
        }

        if (result.isSuccess()) {
            if (repository.update(memory)) {
                result.setMemory(memory);
            } else {
                String message = String.format("Memory id %s was not found.", memory.getId());
                result.addErrorMessage(message);
            }
        }
        return result;
    }

    public MemoryResult deleteById(int memoryId) throws DataAccessException {
        MemoryResult result = new MemoryResult();
        if (!repository.deleteById(memoryId)) {
            String message = String.format("Memory id %s was not found.", memoryId);
            result.addErrorMessage(message);
        }
        return result;
    }

    private MemoryResult validate(Memory memory) {
        MemoryResult result = new MemoryResult();

        if (memory == null) {
            result.addErrorMessage("Memory cannot be null.");
            return result;
        }

        if (memory.getFrom() == null || memory.getFrom().isBlank()) {
            result.addErrorMessage("Memory `from` is required.");
        }

        if (memory.getContent() == null || memory.getContent().isBlank()) {
            result.addErrorMessage("Memory `content` is required.");
        }

        return result;
    }
}