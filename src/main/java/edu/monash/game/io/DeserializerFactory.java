package edu.monash.game.io;

import org.apache.commons.io.FilenameUtils;

import java.util.HashMap;
import java.util.Map;

public class DeserializerFactory {

    private final Map<String, Deserializer> deserializers = new HashMap<>();

    public void registerDeserializer(Deserializer deserializer) {
        deserializers.put(deserializer.getSupportedFileExtension(), deserializer);
    }

    public Deserializer getDeserializerFor(String filename) {
        String fileExtension = FilenameUtils.getExtension(filename);

        if (deserializers.containsKey(fileExtension))
            return deserializers.get(fileExtension);

        throw new IllegalArgumentException("Unsupported file type.");
    }

}
