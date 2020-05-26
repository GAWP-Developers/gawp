package guru.springframework.services;

import guru.springframework.commands.ApplicationForm;
import guru.springframework.domain.Application;

import java.util.List;

public interface ApplicationService {
    List<Application> listAll();

    Application getById(Long id);

    Application saveOrUpdate(Application application);

    void delete(Long id);

    Application saveOrUpdateApplicationForm(ApplicationForm applicationForm);
}
