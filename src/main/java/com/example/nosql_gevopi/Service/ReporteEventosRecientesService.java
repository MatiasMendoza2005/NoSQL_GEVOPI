package com.example.nosql_gevopi.Service;

import com.example.nosql_gevopi.Entity.ReporteDashboard;
import com.example.nosql_gevopi.Entity.ReporteDashboard.*;
import com.example.nosql_gevopi.Repositorios.ReporteDashboardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class ReporteEventosRecientesService {

    private final ReporteDashboardRepository repository;

    public ReporteEventosRecientesService(ReporteDashboardRepository repository) {
        this.repository = repository;
    }

    private static final String ID_DASHBOARD = "reporte-principal";

    private ReporteDashboard getOrCreateDashboard() {
        return repository.findById(ID_DASHBOARD).orElseGet(() -> {
            ReporteDashboard nuevo = new ReporteDashboard();
            nuevo.setId(ID_DASHBOARD);
            return nuevo;
        });
    }

    public void agregarVoluntario(String id, String nombre, LocalDateTime fecha, String disponibilidad) {
        ReporteDashboard dashboard = getOrCreateDashboard();

        VoluntarioRegistro registro = new VoluntarioRegistro();
        registro.setIdVoluntario(id);
        registro.setNombre(nombre);
        registro.setFechaRegistro(fecha);
        registro.setDisponibilidad(disponibilidad);

        dashboard.setUltimos5VoluntariosRegistrados(insertarAlInicio(dashboard.getUltimos5VoluntariosRegistrados(), registro));
        repository.save(dashboard);
    }

    public void agregarPedidoAyuda(String idVoluntario, String tipo, String nivel, LocalDateTime fecha) {
        ReporteDashboard dashboard = getOrCreateDashboard();

        PedidoAyuda pedido = new PedidoAyuda();
        pedido.setIdVoluntario(idVoluntario);
        pedido.setTipo(tipo);
        pedido.setNivel(nivel);
        pedido.setFechaRegistro(fecha);

        dashboard.setUltimos5PedidosAyuda(insertarAlInicio(dashboard.getUltimos5PedidosAyuda(), pedido));
        repository.save(dashboard);
    }

    public void agregarTestRecibido(TestRecibido test) {
        ReporteDashboard dashboard = getOrCreateDashboard();
        dashboard.setUltimos5TestsRecibidos(insertarAlInicio(dashboard.getUltimos5TestsRecibidos(), test));
        repository.save(dashboard);
    }

    public void agregarCapacitacion(CapacitacionAsignada cap) {
        ReporteDashboard dashboard = getOrCreateDashboard();
        dashboard.setUltimas5CapacitacionesAsignadas(insertarAlInicio(dashboard.getUltimas5CapacitacionesAsignadas(), cap));
        repository.save(dashboard);
    }

    public void agregarNecesidad(NecesidadDetectada necesidad) {
        ReporteDashboard dashboard = getOrCreateDashboard();
        dashboard.setUltimas5NecesidadesDetectadas(insertarAlInicio(dashboard.getUltimas5NecesidadesDetectadas(), necesidad));
        repository.save(dashboard);
    }

    // Genérico: mantiene solo 5 elementos
    private <T> List<T> insertarAlInicio(List<T> listaOriginal, T nuevo) {
        if (listaOriginal == null) listaOriginal = new LinkedList<>();
        listaOriginal.add(0, nuevo);
        if (listaOriginal.size() > 5) listaOriginal = listaOriginal.subList(0, 5);
        return listaOriginal;
    }
}

