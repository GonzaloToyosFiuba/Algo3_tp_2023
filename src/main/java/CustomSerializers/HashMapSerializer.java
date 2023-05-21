package CustomSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HashMapSerializer extends JsonSerializer<HashMap<UUID, Serializable>> {
    @Override
    public void serialize(HashMap<UUID, Serializable> HashMap, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();

        for (Map.Entry<UUID, Serializable> entry : HashMap.entrySet()) {
            jsonGenerator.writeObject(entry.getValue());
        }

        jsonGenerator.writeEndArray();
    }
}
