package com.example.nosql_gevopi.Repositorios;

import com.example.nosql_gevopi.Entity.HistorialUbicacion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public interface HistorialUbicacionRepository extends MongoRepository<HistorialUbicacion, String> {
    List<HistorialUbicacion> findByVoluntarioIdOrderByFechaDesc(String voluntarioId);
    List<HistorialUbicacion> findByVoluntarioIdAndFechaAfter(String voluntarioId, LocalDateTime fecha);
    List<HistorialUbicacion> findByFechaAfter(LocalDateTime fecha);
}
