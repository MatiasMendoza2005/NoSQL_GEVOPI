package com.example.nosql_gevopi.Resolvers;

import com.example.nosql_gevopi.Entity.HistorialUbicacion;
import com.example.nosql_gevopi.Entity.SolicitudAyuda;
import com.example.nosql_gevopi.Repositorios.HistorialUbicacionRepository;
import com.example.nosql_gevopi.Repositorios.SolicitudAyudaRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
public class ReporteVoluntariosResolver {

    private final SolicitudAyudaRepository solicitudRepo;
    private final HistorialUbicacionRepository historialRepo;

    public ReporteVoluntariosResolver(
            SolicitudAyudaRepository solicitudRepo,
            HistorialUbicacionRepository historialRepo
    ) {
        this.solicitudRepo = solicitudRepo;
        this.historialRepo = historialRepo;
    }

    @QueryMapping
    public Map<String, Object> generarReporteVoluntarios(
            @Argument String voluntarioId
    ) {
        LocalDateTime ahora = LocalDateTime.now(ZoneOffset.UTC);
        System.out.println("Fecha actual del sistema (UTC): " + ahora);

        LocalDateTime fechaLimite = ahora.minusMonths(1);

        System.out.println("Fecha límite (UTC): " + fechaLimite);


        List<SolicitudAyuda> solicitudes = (voluntarioId != null)
                ? solicitudRepo.findByVoluntarioId(voluntarioId)
                : solicitudRepo.findAll();

        List<HistorialUbicacion> ubicaciones = (voluntarioId != null)
                ? historialRepo.findByVoluntarioIdAndFechaAfter(voluntarioId, fechaLimite)
                : historialRepo.findByFechaAfter(fechaLimite);

        return Map.of(
                "historialSolicitudes", solicitudes,
                "ubicacionesUltimoMes", ubicaciones
        );
    }
}
