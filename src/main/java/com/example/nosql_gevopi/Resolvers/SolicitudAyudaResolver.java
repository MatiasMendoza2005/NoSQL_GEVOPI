package com.example.nosql_gevopi.Resolvers;

import com.example.nosql_gevopi.Entity.NivelEmergencia;
import com.example.nosql_gevopi.Entity.SolicitudAyuda;
import com.example.nosql_gevopi.Repositorios.SolicitudAyudaRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SolicitudAyudaResolver {

    private final SolicitudAyudaRepository solicitudAyudaRepository;

    public SolicitudAyudaResolver(SolicitudAyudaRepository solicitudAyudaRepository) {
        this.solicitudAyudaRepository = solicitudAyudaRepository;
    }

    @QueryMapping
    public List<SolicitudAyuda> obtenerSolicitudesPorVoluntario(@Argument String voluntarioId) {
        return solicitudAyudaRepository.findByVoluntarioId(voluntarioId);
    }

    @QueryMapping
    public List<SolicitudAyuda> obtenerTodasSolicitudes() {
        return solicitudAyudaRepository.findAll();
    }

    @MutationMapping
    public SolicitudAyuda crearSolicitudAyuda(
            @Argument String tipo,
            @Argument String voluntarioId,
            @Argument String descripcion,
            @Argument String estado,
            @Argument NivelEmergencia nivelEmergencia,
            @Argument Float latitud,
            @Argument Float longitud,
            @Argument String fecha,
            @Argument List<Integer> ciVoluntariosAcudir) {

        SolicitudAyuda solicitud = new SolicitudAyuda();
        solicitud.setTipo(tipo);
        solicitud.setVoluntarioId(voluntarioId);
        solicitud.setDescripcion(descripcion);
        solicitud.setEstado(estado);
        solicitud.setNivelEmergencia(nivelEmergencia);
        solicitud.setLatitud(latitud);
        solicitud.setLongitud(longitud);
        solicitud.setFecha(fecha);
        solicitud.setCiVoluntariosAcudir(ciVoluntariosAcudir);  // Asignación del nuevo campo

        return solicitudAyudaRepository.save(solicitud);
    }

    @MutationMapping
    public SolicitudAyuda actualizarSolicitudEnProgreso(
            @Argument String id,
            @Argument List<Integer> nuevosCIs) {

        SolicitudAyuda solicitud = solicitudAyudaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // Actualizar estado
        solicitud.setEstado("en progreso");

        // Agregar nuevos CIs a la lista (manejo de lista nula)
        if (solicitud.getCiVoluntariosAcudir() == null) {
            solicitud.setCiVoluntariosAcudir(new ArrayList<>());
        }
        solicitud.getCiVoluntariosAcudir().addAll(nuevosCIs);

        return solicitudAyudaRepository.save(solicitud);
    }

    @MutationMapping
    public SolicitudAyuda marcarSolicitudRespondida(
            @Argument String id) {

        SolicitudAyuda solicitud = solicitudAyudaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // Actualizar estado y fecha de respuesta
        solicitud.setEstado("respondido");
        solicitud.setFechaRespondida(LocalDateTime.now().toString());

        return solicitudAyudaRepository.save(solicitud);
    }
}