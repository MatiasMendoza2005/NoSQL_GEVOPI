package com.example.nosql_gevopi.Service;

import com.example.nosql_gevopi.Entity.ReporteDashboard;
import com.example.nosql_gevopi.Repositorios.ReporteDashboardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReporteGeneralService {

    private final ReporteDashboardRepository repository;

    public ReporteGeneralService(ReporteDashboardRepository repository) {
        this.repository = repository;
    }

    public void actualizarDatosBasicosPorNuevoTest() {
        final String ID_DASHBOARD = "reporte-principal";

        ReporteDashboard dashboard = repository.findById(ID_DASHBOARD).orElseGet(() -> {
            ReporteDashboard nuevo = new ReporteDashboard();
            nuevo.setId(ID_DASHBOARD);
            nuevo.setFuenteDatos("Debezium SQL Server");
            nuevo.setTotalTestsRealizados(0);
            nuevo.setTestsUltimas24Horas(0);
            return nuevo;
        });

        //Actualiza campos básicos
        dashboard.setUltimaActualizacion(LocalDateTime.now());
        dashboard.setFuenteDatos("Debezium SQL Server");

        //Incrementa total
        Integer actual = dashboard.getTotalTestsRealizados() != null ? dashboard.getTotalTestsRealizados() : 0;
        dashboard.setTotalTestsRealizados(actual + 1);

        //Recalcula tests en las últimas 24 horas
        if (dashboard.getUltimos5TestsRecibidos() != null) {
            long count = dashboard.getUltimos5TestsRecibidos().stream()
                    .flatMap(t -> t.getPreguntas().stream())
                    .flatMap(p -> p.getDetalleRespuestas().stream())
                    .filter(r -> r.getFecha().isAfter(LocalDateTime.now().minusHours(24)))
                    .count();

            dashboard.setTestsUltimas24Horas((int) count);
        }

        repository.save(dashboard);
    }
}

