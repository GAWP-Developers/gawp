package com.gawpdevelopers.gawp.repositories;

import com.gawpdevelopers.gawp.domain.Applicant;

import com.gawpdevelopers.gawp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ApplicantRepository extends CrudRepository<Applicant, Long> {
   Optional<Applicant> findByApiID(String apid);
}
