package com.gawpdevelopers.gawp.services;

import com.gawpdevelopers.gawp.commands.ApplicationForm;
import com.gawpdevelopers.gawp.domain.Application;

import java.util.List;

public interface ApplicationService {
    List<Application> listAll();

    Application getById(Long id);

    Application saveOrUpdate(Application application);

    void delete(Long id);

    Application saveOrUpdateApplicationForm(ApplicationForm applicationForm);
}
