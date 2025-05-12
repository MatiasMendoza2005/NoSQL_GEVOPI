package com.example.nosql_gevopi.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@Document(collection = "historial_ubicaciones")
public class HistorialUbicacion {

    @Id
    private String id;

    private String voluntarioId;
    private Float lat;
    private Float lon;
    private LocalDateTime fecha;

    public HistorialUbicacion() {}
}
