package CustomSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HashMapTareasSerializer extends JsonSerializer<HashMap<UUID, Object>> {
    @Override
    public void serialize(HashMap<UUID, Object> HashMap, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();

        for (Map.Entry<UUID, Object> entry : HashMap.entrySet()) {
            jsonGenerator.writeObject(entry.getValue());
        }

        jsonGenerator.writeEndArray();
    }
}
