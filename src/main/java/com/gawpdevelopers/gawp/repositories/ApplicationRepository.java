package com.gawpdevelopers.gawp.repositories;

import com.gawpdevelopers.gawp.domain.Application;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationRepository extends CrudRepository<Application, Long> {
}