package com.example.nosql_gevopi.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "solicitudes_ayuda")
public class SolicitudAyuda {
    @Id
    private String id;
    private String tipo;
    private String voluntarioId;
    private String descripcion;
    private String estado;
    private NivelEmergencia nivelEmergencia;
    private Float latitud;
    private Float longitud;
    private String fecha;
    private String fechaRespondida;
    private List<Integer> ciVoluntariosAcudir = new ArrayList<>();
}

