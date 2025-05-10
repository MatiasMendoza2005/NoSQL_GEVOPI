package com.example.nosql_gevopi.Repositorios;

import com.example.nosql_gevopi.Entity.SolicitudAyuda;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SolicitudAyudaRepository extends MongoRepository<SolicitudAyuda, String> {
    List<SolicitudAyuda> findByVoluntarioId(String voluntarioId);
}
