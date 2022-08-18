package espe.edu.ec.batch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Aula {

    @Id
    private  String id;

    private  String paralelo;

    private Integer nivel;

    private List<EstudianteDTO> estudiantes;
}
