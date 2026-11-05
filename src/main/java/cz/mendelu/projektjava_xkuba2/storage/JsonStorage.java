package cz.mendelu.projektjava_xkuba2.storage;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class JsonStorage {

    private final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    /**
     *
     * @param items
     * @param file
     * @param <T>
     * @throws IOException
     */
    public <T> void saveList(List<T> items, Path file) throws IOException {
        Path parent = file.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        mapper.writeValue(file.toFile(), items);
    }

    /**
     * @param file
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> List<T> loadList(Path file, Class<T> elementType) throws IOException {
        if (!Files.exists(file)) {
            return new ArrayList<>();
        }
        return mapper.readValue(file.toFile(),
                mapper.getTypeFactory().constructCollectionType(List.class, elementType));
    }
}
