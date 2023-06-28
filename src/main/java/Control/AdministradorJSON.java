package Control;

import Calendario.Calendario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.nio.file.Path;

public class AdministradorJSON {
    private static final ObjectMapper mapper = new ObjectMapper();
    public void serializar(Calendario miCalendario, Writer w) throws IOException {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String serializacionObtenida = mapper.writeValueAsString(miCalendario);

        w.write(serializacionObtenida);
    }

    public Calendario deserializar(Reader r) throws IOException {
        return mapper.readValue(r, Calendario.class);
    }
}
