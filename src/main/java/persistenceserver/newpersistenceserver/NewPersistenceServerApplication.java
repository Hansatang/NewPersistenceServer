package persistenceserver.newpersistenceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@EntityScan({"persistenceserver.DatabaseModels"})
@EnableJpaRepositories(basePackages = {"persistenceserver.jparepositories"})
public class NewPersistenceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewPersistenceServerApplication.class, args);
    }

}
