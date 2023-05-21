package CustomDeserializers;

import Calendario.Evento;
import Calendario.Tarea;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class HashMapTareaDeserializer extends StdDeserializer<HashMap<UUID, Tarea>> {
    protected HashMapTareaDeserializer(Class<?> vc) {
        super(vc);
    }

    public HashMapTareaDeserializer() {
        this(null);
    }


    @Override
    public HashMap<UUID, Tarea> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper objectMapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode root = objectMapper.readTree(jsonParser);

        HashMap<UUID, Tarea> serializableHashMap = new HashMap<>();

        for (JsonNode personaNode : root) {
            String id = personaNode.get("id").asText();

            Tarea nuevo = objectMapper.treeToValue(personaNode, Tarea.class);
            serializableHashMap.put(UUID.fromString(id), nuevo);
        }
        return serializableHashMap;
    }
}
