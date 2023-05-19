import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PersistenciaTest {
    public static ObjectMapper objectMapper = new ObjectMapper();
    @Test
    public void persistenciaTipoFrecuencia(){
        String serializacionEsperada = "{\"tipo\":\"diaria\",\"intervalo\":5}";
        String serializacionObtenida = "";

        TipoFrecuencia tipo = new Diaria(5);

        try {
            serializacionObtenida =  objectMapper.writeValueAsString(tipo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assertEquals(serializacionEsperada, serializacionObtenida);
    }
}
