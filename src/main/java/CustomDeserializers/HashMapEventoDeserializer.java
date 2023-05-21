package CustomDeserializers;

import Calendario.Evento;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;


public class HashMapEventoDeserializer extends StdDeserializer<HashMap<UUID, Evento>> {
    protected HashMapEventoDeserializer(Class<?> vc) {
        super(vc);
    }

    public HashMapEventoDeserializer() {
        this(null);
    }


    @Override
    public HashMap<UUID, Evento> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper objectMapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode root = objectMapper.readTree(jsonParser);

        HashMap<UUID, Evento> serializableHashMap = new HashMap<>();

        for (JsonNode personaNode : root) {
            String id = personaNode.get("id").asText();

            Evento nuevo = objectMapper.treeToValue(personaNode, Evento.class);
            serializableHashMap.put(UUID.fromString(id), nuevo);
        }
        return serializableHashMap;
    }
}


/*
public class HashMapDeserializer<T> extends StdDeserializer<HashMap<UUID, T>> {

    private Class<T> valueType;

    protected HashMapDeserializer(Class<T> valueType) {
        super(HashMap.class);
        this.valueType = valueType;
    }

    public HashMapDeserializer() {
        this(null);
    }

    @Override
    public HashMap<UUID, T> deserialize(JsonParser jp, DeserializationContext deserializationContext)
            throws IOException, JacksonException {

        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode root = mapper.readTree(jp);

        if (root == null || root.isNull()) {
            return null; // Devuelve null si el nodo JSON es nulo
        }

        HashMap<UUID, T> serializableHashMap = new HashMap<>();

        for (JsonNode node : root) {
            String id = node.get("id").asText();
            T value = mapper.treeToValue(node, valueType);
            serializableHashMap.put(UUID.fromString(id), value);
        }
        return serializableHashMap;
    }
}

 */
