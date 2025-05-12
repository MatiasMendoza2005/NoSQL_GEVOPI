package com.example.nosql_gevopi.Repositorios;

import com.example.nosql_gevopi.Entity.ReporteDashboard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DashboardRepository extends MongoRepository<ReporteDashboard, String> {

    // Método para encontrar el reporte principal
    ReporteDashboard findByIdEquals(String id);

    // Método para encontrar reportes recientes
    List<ReporteDashboard> findByUltimaActualizacionAfter(LocalDateTime fecha);

    // Consulta para obtener reportes por fuente de datos
    @Query("{ 'fuenteDatos': ?0 }")
    List<ReporteDashboard> obtenerReportesPorFuente(String fuente);

    // Método para actualizar un reporte existente o crear uno si no existe
    default ReporteDashboard actualizarOCrearReporte(ReporteDashboard reporte) {
        return save(reporte);
    }
}