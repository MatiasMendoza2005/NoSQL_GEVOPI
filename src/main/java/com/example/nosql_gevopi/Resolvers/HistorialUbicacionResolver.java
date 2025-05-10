package com.example.nosql_gevopi.Resolvers;

import com.example.nosql_gevopi.Entity.HistorialUbicacion;
import com.example.nosql_gevopi.Repositorios.HistorialUbicacionRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Controller
public class HistorialUbicacionResolver {

    private final HistorialUbicacionRepository repository;

    public HistorialUbicacionResolver(HistorialUbicacionRepository repository) {
        this.repository = repository;
    }

    @MutationMapping
    public HistorialUbicacion crearHistorialUbicacion(
            @Argument String voluntarioId,
            @Argument Double lat,
            @Argument Double lon) {

        HistorialUbicacion historial = new HistorialUbicacion();
        historial.setVoluntarioId(voluntarioId);
        historial.setLat(lat);
        historial.setLon(lon);
        historial.setFecha(LocalDateTime.now());

        return repository.save(historial);
    }

    @QueryMapping
    public List<HistorialUbicacion> obtenerHistorialPorVoluntario(
            @Argument String voluntarioId) {
        return repository.findByVoluntarioIdOrderByFechaDesc(voluntarioId);
    }

    @QueryMapping
    public List<HistorialUbicacion> obtenerTodosHistoriales() {
        return repository.findAll();
    }
}
