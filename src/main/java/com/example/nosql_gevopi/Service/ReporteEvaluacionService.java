package com.example.nosql_gevopi.Service;

import com.example.nosql_gevopi.Entity.ReporteDashboard;
import com.example.nosql_gevopi.Entity.ReporteDashboard.*;
import com.example.nosql_gevopi.Repositorios.ReporteDashboardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReporteEvaluacionService {

    private final ReporteDashboardRepository repository;

    public ReporteEvaluacionService(ReporteDashboardRepository repository) {
        this.repository = repository;
    }

    public void actualizarResumenEvaluacion() {
        final String ID_DASHBOARD = "reporte-principal";
        ReporteDashboard dashboard = repository.findById(ID_DASHBOARD).orElse(null);
        if (dashboard == null) return;

        dashboard.setUltimaActualizacion(LocalDateTime.now());

        //Necesidad
        Necesidad necesidad = new Necesidad();
        necesidad.setTipo("Emocional");
        necesidad.setValor(0.65); // ejemplo: 65% de los voluntarios tienen necesidades emocionales
        dashboard.setNecesidad(necesidad);

        //Capacitacion
        Capacitacion capacitacion = new Capacitacion();
        capacitacion.setTipo("Primeros auxilios");
        capacitacion.setValor(0.75); // ejemplo: 75% completaron esta capacitación
        dashboard.setCapacitacion(capacitacion);

        //Promedio físico
        PromedioFisico promedioFisico = new PromedioFisico();
        promedioFisico.setValor(3.8); // escala del 1 al 5
        promedioFisico.setSintomaFrecuente("Fatiga muscular");
        dashboard.setPromedioFisico(promedioFisico);

        //Promedio emocional
        PromedioEmocional promedioEmocional = new PromedioEmocional();
        promedioEmocional.setValor(4.2);
        promedioEmocional.setSintomaFrecuente("Ansiedad");
        dashboard.setPromedioEmocional(promedioEmocional);

        //Distribución de estrés
        DistribucionEstres estres = new DistribucionEstres();

        NivelEstres bajo = new NivelEstres();
        bajo.setPorcentaje(25.0);
        bajo.setCantidadVoluntarios(10);

        NivelEstres moderado = new NivelEstres();
        moderado.setPorcentaje(50.0);
        moderado.setCantidadVoluntarios(20);

        NivelEstres alto = new NivelEstres();
        alto.setPorcentaje(25.0);
        alto.setCantidadVoluntarios(10);

        estres.setBajo(bajo);
        estres.setModerado(moderado);
        estres.setAlto(alto);
        estres.setTendencia("Moderado en aumento");

        dashboard.setDistribucionEstres(estres);

        repository.save(dashboard);
    }
}

