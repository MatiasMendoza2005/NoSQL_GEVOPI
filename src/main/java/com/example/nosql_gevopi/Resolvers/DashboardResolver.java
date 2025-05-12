// DashboardResolver.java
package com.example.nosql_gevopi.Resolvers;

import com.example.nosql_gevopi.Entity.ReporteDashboard;
import com.example.nosql_gevopi.Repositorios.DashboardRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class DashboardResolver {

    private final DashboardRepository dashboardRepository;

    public DashboardResolver(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    @QueryMapping
    public ReporteDashboard reporteDashboard() {
        return getOrCreateReportePrincipal();
    }

    @QueryMapping
    public List<ReporteDashboard> reportesRecientes(@Argument int dias) {
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(dias);
        return dashboardRepository.findByUltimaActualizacionAfter(fechaLimite);
    }

    @MutationMapping
    public Boolean actualizarDashboard() {
        ReporteDashboard reporte = getOrCreateReportePrincipal();

        reporte.setUltimaActualizacion(LocalDateTime.now());
        reporte.setFuenteDatos("Sistema Principal");

        // Lógica de actualización de tests
        if(reporte.getTotalTestsRealizados() == null) reporte.setTotalTestsRealizados(0);
        reporte.setTotalTestsRealizados(reporte.getTotalTestsRealizados() + 1);

        dashboardRepository.save(reporte);
        return true;
    }

    @MutationMapping
    public ReporteDashboard agregarVoluntario(@Argument String idVoluntario,
                                              @Argument String nombre,
                                              @Argument String disponibilidad) {
        ReporteDashboard reporte = getOrCreateReportePrincipal();

        ReporteDashboard.VoluntarioRegistro nuevo = new ReporteDashboard.VoluntarioRegistro(
                idVoluntario,
                nombre,
                LocalDateTime.now(),
                disponibilidad
        );

        gestionarListaVoluntarios(reporte, nuevo);
        return dashboardRepository.save(reporte);
    }

    @MutationMapping
    public ReporteDashboard registrarPedidoAyuda(@Argument String idVoluntario,
                                                 @Argument String tipo,
                                                 @Argument String nivel) {
        ReporteDashboard reporte = getOrCreateReportePrincipal();

        ReporteDashboard.PedidoAyuda nuevo = new ReporteDashboard.PedidoAyuda(
                idVoluntario,
                tipo,
                LocalDateTime.now(),
                nivel
        );

        gestionarListaPedidos(reporte, nuevo);
        return dashboardRepository.save(reporte);
    }

    private void gestionarListaVoluntarios(ReporteDashboard reporte,
                                           ReporteDashboard.VoluntarioRegistro nuevo) {
        List<ReporteDashboard.VoluntarioRegistro> lista = reporte.getUltimos5VoluntariosRegistrados();
        lista.add(0, nuevo);
        if(lista.size() > 5) {
            reporte.setUltimos5VoluntariosRegistrados(lista.subList(0, 5));
        }
    }

    private void gestionarListaPedidos(ReporteDashboard reporte,
                                       ReporteDashboard.PedidoAyuda nuevo) {
        List<ReporteDashboard.PedidoAyuda> lista = reporte.getUltimos5PedidosAyuda();
        lista.add(0, nuevo);
        if(lista.size() > 5) {
            reporte.setUltimos5PedidosAyuda(lista.subList(0, 5));
        }
    }

    private ReporteDashboard getOrCreateReportePrincipal() {
        return dashboardRepository.findById("reporte-principal")
                .orElseGet(() -> {
                    ReporteDashboard nuevo = new ReporteDashboard();
                    nuevo.setId("reporte-principal");
                    nuevo.setUltimaActualizacion(LocalDateTime.now());
                    return dashboardRepository.save(nuevo);
                });
    }
}