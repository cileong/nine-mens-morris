package edu.monash.game.io;

import org.apache.commons.io.FilenameUtils;

import java.util.HashMap;
import java.util.Map;

public class SerializerFactory {

    private final Map<String, Serializer> serializers = new HashMap<>();

    public void registerSerializer(Serializer serializer) {
        serializers.put(serializer.getSupportedFileExtension(), serializer);
    }

    public Serializer getSerializerFor(String filename) {
        String fileExtension = FilenameUtils.getExtension(filename);

        if (serializers.containsKey(fileExtension))
            return serializers.get(fileExtension);

        throw new IllegalArgumentException("Unsupported file type.");
    }

}
