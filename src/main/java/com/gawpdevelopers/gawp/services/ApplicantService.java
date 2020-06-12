package com.gawpdevelopers.gawp.services;

import com.gawpdevelopers.gawp.commands.ApplicationForm;
import com.gawpdevelopers.gawp.domain.Applicant;
import com.gawpdevelopers.gawp.domain.Application;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ApplicantService  {
    List<Applicant> listAll();

    Applicant getById(Long id);
    Applicant getByApiId(String apid);

    Applicant saveOrUpdate(Applicant applicant) ;

    void delete(Long id);


}
