package edu.monash.game.io;

import org.apache.commons.io.FilenameUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * A factory for creating serializers.
 */
public class SerializerFactory {

    /**
     * A map of file extensions to serializers.
     */
    private final Map<String, Serializer> serializers = new HashMap<>();

    /**
     * Registers a serializer with the factory.
     * @param serializer The serializer to register.
     */
    public void registerSerializer(Serializer serializer) {
        serializers.put(serializer.getSupportedFileExtension(), serializer);
    }

    /**
     * Gets the serializer for a given filename of the output file.
     * @param filename The filename.
     * @return A serializer.
     */
    public Serializer getSerializerFor(String filename) {
        String fileExtension = FilenameUtils.getExtension(filename);

        if (serializers.containsKey(fileExtension))
            return serializers.get(fileExtension);

        throw new IllegalArgumentException("Unsupported file type.");
    }

}
