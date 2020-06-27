package com.gawpdevelopers.gawp.services;

import com.gawpdevelopers.gawp.commands.ApplicationForm;
import com.gawpdevelopers.gawp.domain.Advert;
import com.gawpdevelopers.gawp.domain.Applicant;
import com.gawpdevelopers.gawp.domain.Application;
import com.gawpdevelopers.gawp.domain.ApplicationStatus;

import java.util.List;

public interface ApplicationService {
    List<Application> listAll();
    List<Application> listAllByApplicant(Applicant applicant);
    List<Application> listByStatus(ApplicationStatus status);
    Application getById(Long id);
    List<Application> listAllInterviewedByAdvert(Advert advert);
    Application saveOrUpdate(Application application);

    void delete(Long id);

    Application saveOrUpdateApplicationForm(ApplicationForm applicationForm);
}
