package com.example.nosql_gevopi.Resolvers;

import com.example.nosql_gevopi.Entity.ReporteDashboard;
import com.example.nosql_gevopi.Repositorios.ReporteDashboardRepository;
import com.example.nosql_gevopi.Service.ReporteEvaluacionService;
import com.example.nosql_gevopi.Service.ReporteEventosRecientesService;
import com.example.nosql_gevopi.Service.ReporteGeneralService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardResolver {

    private final ReporteDashboardRepository repository;
    private final ReporteGeneralService generalService;
    private final ReporteEvaluacionService evaluacionService;
    private final ReporteEventosRecientesService eventosService;

    public DashboardResolver(
            ReporteDashboardRepository repository,
            ReporteGeneralService generalService,
            ReporteEvaluacionService evaluacionService,
            ReporteEventosRecientesService eventosService) {
        this.repository = repository;
        this.generalService = generalService;
        this.evaluacionService = evaluacionService;
        this.eventosService = eventosService;
    }

    @QueryMapping
    public ReporteDashboard reporteDashboard() {
        return repository.findById("reporte-principal").orElse(null);
    }

    @MutationMapping
    public Boolean actualizarDashboard() {
        generalService.actualizarDatosBasicosPorNuevoTest();
        evaluacionService.actualizarResumenEvaluacion();
        // eventosService.agregarVoluntario(...) si deseas simular carga
        return true;
    }

    @MutationMapping
    public ReporteDashboard insertarDatosPrueba() {
        ReporteDashboard reporte = new ReporteDashboard();
        reporte.setId("reporte-principal");
        reporte.setUltimaActualizacion(LocalDateTime.now());
        reporte.setFuenteDatos("FakeDB");
        reporte.setTotalTestsRealizados(42);
        reporte.setTestsUltimas24Horas(5);

        // Usamos 5 voluntarios activos de los primeros en fake_voluntarios_db
        List<ReporteDashboard.VoluntarioRegistro> voluntarios = List.of(
                new ReporteDashboard.VoluntarioRegistro("1", "Juan Pérez", LocalDateTime.parse("2023-01-01T00:00:00"), "Alta"),
                new ReporteDashboard.VoluntarioRegistro("2", "Ana Gómez", LocalDateTime.parse("2023-02-01T00:00:00"), "Media"),
                new ReporteDashboard.VoluntarioRegistro("3", "María López", LocalDateTime.parse("2023-03-01T00:00:00"), "Alta"),
                new ReporteDashboard.VoluntarioRegistro("5", "Laura Martínez", LocalDateTime.parse("2023-05-20T00:00:00"), "Alta"),
                new ReporteDashboard.VoluntarioRegistro("6", "Pedro Sánchez", LocalDateTime.parse("2023-06-10T00:00:00"), "Baja")
        );
        reporte.setUltimos5VoluntariosRegistrados(voluntarios);

        // Simular pedidos de ayuda
        reporte.setUltimos5PedidosAyuda(List.of(
                new ReporteDashboard.PedidoAyuda("2", "Psicológica", LocalDateTime.now().minusDays(1), "ALTO"),
                new ReporteDashboard.PedidoAyuda("3", "Médica", LocalDateTime.now().minusDays(2), "MEDIO"),
                new ReporteDashboard.PedidoAyuda("5", "Logística", LocalDateTime.now().minusDays(3), "BAJO"),
                new ReporteDashboard.PedidoAyuda("6", "Psicológica", LocalDateTime.now().minusDays(4), "ALTO"),
                new ReporteDashboard.PedidoAyuda("1", "Otro", LocalDateTime.now().minusDays(5), "MEDIO")
        ));

        // Calcular promedios físicos y emocionales con datos de voluntarios
        double sumaFisico = 0, sumaEmocional = 0;
        int count = 0;
        for (int id : List.of(1, 2, 3, 5, 6)) {
            Map<String, Integer> fisico = getVoluntarioFisicoRespuestas(id);
            Map<String, Integer> emocional = getVoluntarioPsicoRespuestas(id);
            sumaFisico += fisico.values().stream().mapToInt(i -> i).sum();
            sumaEmocional += emocional.values().stream().mapToInt(i -> i).sum();
            count += fisico.size(); // misma cantidad para ambos
        }

        double promedioFisico = sumaFisico / count;
        double promedioEmocional = sumaEmocional / count;

        reporte.setPromedioFisico(new ReporteDashboard.PromedioFisico(promedioFisico, "Cansancio"));
        reporte.setPromedioEmocional(new ReporteDashboard.PromedioEmocional(promedioEmocional, "Preocupación constante"));

        // Distribución de estrés simulado
        reporte.setDistribucionEstres(new ReporteDashboard.DistribucionEstres(
                new ReporteDashboard.NivelEstres(20.0, 2),
                new ReporteDashboard.NivelEstres(50.0, 5),
                new ReporteDashboard.NivelEstres(30.0, 3),
                "Leve descenso"
        ));

        // Simulación de test recibido con distribución simple
        ReporteDashboard.TestRecibido test = new ReporteDashboard.TestRecibido();
        test.setIdTest("test-001");
        test.setCategoria("Psicológica");
        test.setNombre("Test de Estrés");
        test.setDescripcion("Evaluación del nivel de estrés");

        ReporteDashboard.PreguntaTest pregunta = new ReporteDashboard.PreguntaTest();
        pregunta.setIdPregunta("p1");
        pregunta.setTextoPregunta("¿Qué tan estresado te sientes?");
        pregunta.setDistribucionRespuestas(List.of(
                new ReporteDashboard.DistribucionRespuesta("Bajo", 2),
                new ReporteDashboard.DistribucionRespuesta("Moderado", 5),
                new ReporteDashboard.DistribucionRespuesta("Alto", 3)
        ));
        pregunta.setDetalleRespuestas(List.of(
                new ReporteDashboard.DetalleRespuesta(LocalDateTime.now().minusDays(1), "2", "Moderado"),
                new ReporteDashboard.DetalleRespuesta(LocalDateTime.now().minusDays(2), "3", "Alto")
        ));
        test.setPreguntas(List.of(pregunta));
        reporte.setUltimos5TestsRecibidos(List.of(test));

        // Capacitaciones simuladas
        reporte.setUltimas5CapacitacionesAsignadas(List.of(
                new ReporteDashboard.CapacitacionAsignada(
                        "cap1", "Gestión emocional", "Curso básico", "Finalizada",
                        LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(2),
                        List.of("1", "2", "5")
                )
        ));

        // Necesidades detectadas simuladas
        reporte.setUltimas5NecesidadesDetectadas(List.of(
                new ReporteDashboard.NecesidadDetectada(
                        "nec1", "Alimentación", "Falta de insumos", "Alta",
                        List.of("1", "3"), LocalDateTime.now().minusDays(7)
                )
        ));

        return repository.save(reporte);
    }

    private Map<String, Integer> getVoluntarioFisicoRespuestas(int id) {
        // simula obtención desde fake_voluntarios_db
        return Map.of(
                "cansancio", 2,
                "dolor_pecho", 1,
                "palpitaciones", 2,
                "congestion_nasal", 1
        );
    }

    private Map<String, Integer> getVoluntarioPsicoRespuestas(int id) {
        return Map.of(
                "preocupacion_constante", 3,
                "dificultad_relajarse", 2,
                "insomnio", 2,
                "inestabilidad_emocional", 2
        );
    }

}

