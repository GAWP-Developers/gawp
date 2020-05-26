package guru.springframework.repositories;

import guru.springframework.domain.Application;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationRepository extends CrudRepository<Application, Long> {
}