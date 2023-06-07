package edu.monash.game.io;

import org.apache.commons.io.FilenameUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * A factory for creating deserializers.
 */
public class DeserializerFactory {

    /**
     * A map of file extensions to deserializers.
     */
    private final Map<String, Deserializer> deserializers = new HashMap<>();

    /**
     * Registers a deserializer with the factory.
     * @param deserializer The deserializer to register.
     */
    public void registerDeserializer(Deserializer deserializer) {
        deserializers.put(deserializer.getSupportedFileExtension(), deserializer);
    }

    /**
     * Gets the deserializer for a given filename of the input file.
     * @param filename The filename.
     * @return A deserializer.
     */
    public Deserializer getDeserializerFor(String filename) {
        String fileExtension = FilenameUtils.getExtension(filename);

        if (deserializers.containsKey(fileExtension))
            return deserializers.get(fileExtension);

        throw new IllegalArgumentException("Unsupported file type.");
    }

}
