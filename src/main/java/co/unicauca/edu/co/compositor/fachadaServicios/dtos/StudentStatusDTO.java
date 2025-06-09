package co.unicauca.edu.co.compositor.fachadaServicios.dtos;

import java.util.Map;


@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class StudentStatusDTO {
    private String idEstudiante; //codigo del estudiante
    private boolean pazYSalvo;
    private Map<String, String> deudas; 
}