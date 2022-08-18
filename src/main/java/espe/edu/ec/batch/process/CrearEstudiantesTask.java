package espe.edu.ec.batch.process;

import espe.edu.ec.batch.model.EstudianteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@RequiredArgsConstructor

public class CrearEstudiantesTask implements Tasklet, StepExecutionListener {
    private static final String BASE_URL = "http://localhost:8080/estudiante";
    private final RestTemplate restTemplate;

    List<EstudianteDTO> estudiantes = new ArrayList<>();

    public CrearEstudiantesTask() {
        this.restTemplate = new RestTemplate(getClient());
    }


    @Override
    public void beforeStep(StepExecution stepExecution) {
        File file = new File("estudiante.txt");
        Scanner escanear;
        try {
            escanear = new Scanner(file);
            while (escanear.hasNextLine()) {
                String line = escanear.nextLine();
                Scanner del = new Scanner(line);
                del.useDelimiter("\\s*,\\s*");
                EstudianteDTO dto = EstudianteDTO.builder()
                        .cedula(del.next())
                        .apellidos(del.next())
                        .nombres(del.next())
                        .nivel(Integer.parseInt(del.next()))
                        .build();
                estudiantes.add(dto);
            }
            escanear.close();
        } catch (FileNotFoundException  e) {

        }
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        for(EstudianteDTO estudiante : estudiantes){
            Map<String, String> map = new HashMap<>();
            map.put("cedula", estudiante.getCedula());
            map.put("apellidos", estudiante.getApellidos());
            map.put("nombres", estudiante.getNombres());
            map.put("nivel", estudiante.getNivel().toString());
            ResponseEntity<EstudianteDTO> response = this.restTemplate.postForEntity(BASE_URL, map, EstudianteDTO.class);
        }
        return null;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    private ClientHttpRequestFactory getClient() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        int connectTimeout = 5000;
        int readTimeout = 5000;
        clientHttpRequestFactory.setConnectTimeout(connectTimeout);
        clientHttpRequestFactory.setReadTimeout(readTimeout);
        return clientHttpRequestFactory;
    }
}
