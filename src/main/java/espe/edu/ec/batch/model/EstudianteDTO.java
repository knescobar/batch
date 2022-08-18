package espe.edu.ec.batch.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "estudiantes")
@TypeAlias("estudiantes")
public class EstudianteDTO {

    @Id
    private  String id;

    @Indexed(name = "idxu_estudiante_cedula", unique = true)
    private  String cedula;

    @Indexed(name = "idxu_estudiante_apellidos", unique = false)
    private String apellidos;

    @Indexed(name = "idxu_estudiante_nombres", unique = false)
    private String nombres;

    @Indexed(name = "idxu_estudiante-nivel", unique = false)
    private Integer nivel;

}
