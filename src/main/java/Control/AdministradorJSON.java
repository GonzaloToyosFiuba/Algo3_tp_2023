package Control;

import Calendario.Calendario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AdministradorJSON {
    private static ObjectMapper mapper = new ObjectMapper();
    public void serializar(Calendario miCalendario){
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String serializacionObtenida = "";
        try {
            serializacionObtenida =  mapper.writeValueAsString(miCalendario);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        String direccion = "src\\main\\resources\\calendario.json";

        try (FileWriter writer = new FileWriter(direccion)) {
            writer.write(serializacionObtenida);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Calendario deserializar(){
        String direccion = "src\\main\\resources\\calendario.json";

        try {
            Calendario miObjeto = mapper.readValue(new File(direccion), Calendario.class);
            return  miObjeto;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
