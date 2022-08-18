package espe.edu.ec.batch.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApplicationValues {
  private final String mongoHost;
  private final String mongoDB;
  private final String mongoUsr;
  private final String mongoPwd;
  private final String mongoAut;

  public ApplicationValues(
      @Value("${examen.mongo.host}") String mongoHost,
      @Value("${examen.mongo.db}") String mongoDB,
      @Value("${examen.mongo.usr}") String mongoUsr,
      @Value("${examen.mongo.pwd}") String mongoPwd,
      @Value("${examen.mongo.aut}") String mongoAut) {

    this.mongoHost = mongoHost;
    this.mongoDB = mongoDB;
    this.mongoUsr = mongoUsr;
    this.mongoPwd = mongoPwd;
    this.mongoAut = mongoAut;
  }
}
