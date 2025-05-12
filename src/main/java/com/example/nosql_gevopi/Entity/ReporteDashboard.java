package com.example.nosql_gevopi.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document(collection = "reportes_dashboard")
public class ReporteDashboard {

    @Id
    private String id;
    private LocalDateTime ultimaActualizacion;
    private String fuenteDatos;
    private Integer totalTestsRealizados;
    private Integer testsUltimas24Horas;

    private Necesidad necesidad;
    private Capacitacion capacitacion;
    private PromedioFisico promedioFisico;
    private PromedioEmocional promedioEmocional;

    private List<VoluntarioRegistro> ultimos5VoluntariosRegistrados;
    private List<PedidoAyuda> ultimos5PedidosAyuda;
    private List<TestRecibido> ultimos5TestsRecibidos;
    private List<CapacitacionAsignada> ultimas5CapacitacionesAsignadas;
    private List<NecesidadDetectada> ultimas5NecesidadesDetectadas;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Necesidad {
        private Double valor;
        private String tipo;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Capacitacion {
        private Double valor;
        private String tipo;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class PromedioFisico {
        private Double valor;
        private String sintomaFrecuente;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class PromedioEmocional {
        private Double valor;
        private String sintomaFrecuente;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class VoluntarioRegistro {
        private String idVoluntario;
        private String nombre;
        private LocalDateTime fechaRegistro;
        private String disponibilidad;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class PedidoAyuda {
        private String idVoluntario;
        private String tipo;
        private LocalDateTime fechaRegistro;
        private String nivel;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class TestRecibido {
        private String idTest;
        private String categoria;
        private String nombre;
        private String descripcion;
        private List<PreguntaTest> preguntas;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class PreguntaTest {
        private String idPregunta;
        private String textoPregunta;
        private List<DistribucionRespuesta> distribucionRespuestas;
        private List<DetalleRespuesta> detalleRespuestas;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class DistribucionRespuesta {
        private String opcion;
        private Integer cantidad;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class DetalleRespuesta {
        private LocalDateTime fecha;
        private String idVoluntario;
        private String respuesta;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CapacitacionAsignada {
        private String idCapacitacion;
        private String nombre;
        private String descripcion;
        private String estado;
        private LocalDateTime fechaAsignacion;
        private LocalDateTime fechaFinalizacion;
        private List<String> voluntariosAsignados;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class NecesidadDetectada {
        private String idNecesidad;
        private String tipo;
        private String descripcion;
        private String urgencia;
        private List<String> voluntariosAfectados;
        private LocalDateTime fechaDeteccion;
    }
}
