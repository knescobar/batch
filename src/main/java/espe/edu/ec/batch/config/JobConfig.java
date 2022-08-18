package espe.edu.ec.batch.config;

import espe.edu.ec.batch.process.AsignaiconEstudiatesTask;
import espe.edu.ec.batch.process.CrearEstudiantesTask;
import espe.edu.ec.batch.process.GeneracionListadoTask;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfig {
    private final JobBuilderFactory jobs;
    private final StepBuilderFactory steps;
    private final MongoTemplate mongoTemplate;

    @Bean
    protected Step leerEstudiantes() {
        return steps.get("leerEstudiantes").tasklet(new CrearEstudiantesTask()).build();
    }

    @Bean
    protected Step asignacionEstudiantes() {
        return steps.get("asignacionEstudiantes").tasklet(new AsignaiconEstudiatesTask()).build();
    }

    @Bean
    protected Step generacionListado() {
        return steps.get("generacionListado").tasklet(new GeneracionListadoTask()).build();
    }


    @Bean
    public Job processTextFileJob() {
        return jobs.get("processTextFileJob")
                .start(leerEstudiantes())
                .next(asignacionEstudiantes())
                .next(generacionListado())
                .build();
    }

}
