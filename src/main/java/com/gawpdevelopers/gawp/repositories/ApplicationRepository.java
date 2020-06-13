package com.gawpdevelopers.gawp.repositories;

import com.gawpdevelopers.gawp.domain.Applicant;
import com.gawpdevelopers.gawp.domain.Application;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApplicationRepository extends CrudRepository<Application, Long> {
//    List<Application> findByApplicantID(Long id);
    List<Application> findByApplicant(Applicant applicant);
}