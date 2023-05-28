package CustomDeserializers;

import Calendario.Agendable;
import Calendario.Evento;
import Calendario.Tarea;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;


public class HashMapDeserializer extends StdDeserializer<HashMap<UUID, Agendable>> {
    protected HashMapDeserializer(Class<?> vc) {
        super(vc);
    }

    public HashMapDeserializer() {
        this(null);
    }

    @Override
    public HashMap<UUID, Agendable> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper objectMapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode root = objectMapper.readTree(jsonParser);

        HashMap<UUID, Agendable> agendableHashMap = new HashMap<>();

        for (JsonNode personaNode : root) {
            String id = personaNode.get("id").asText();

            if(personaNode.get("tipoAgendable").asText().equals("Evento")) {
                Agendable nuevo = objectMapper.treeToValue(personaNode, Evento.class);
                agendableHashMap.put(UUID.fromString(id), nuevo);
            } else if (personaNode.get("tipoAgendable").asText().equals("Tarea")){
                Agendable nuevo = objectMapper.treeToValue(personaNode, Tarea.class);
                agendableHashMap.put(UUID.fromString(id), nuevo);
            }
        }
        return agendableHashMap;
    }
}