package com.gawpdevelopers.gawp.services;

import com.gawpdevelopers.gawp.commands.ApplicationForm;
import com.gawpdevelopers.gawp.converters.ApplicationFormToApplication;
import com.gawpdevelopers.gawp.domain.Application;
import com.gawpdevelopers.gawp.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private ApplicationRepository applicationRepository;
    private ApplicationFormToApplication applicationFormToapplication;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository, ApplicationFormToApplication applicationFormToApplication) {
        this.applicationRepository = applicationRepository;
        this.applicationFormToapplication = applicationFormToApplication;
    }

    @Override
    public List<Application> listAll() {
        List<Application> applications = new ArrayList<>();
        applicationRepository.findAll().forEach(applications::add); //fun with Java 8
        return applications;
    }

    @Override
    public List<Application> listAllById(Long id) {
        List<Application> applications = new ArrayList<>();
        applicationRepository.findByApplicantID(id).forEach(applications::add);
        return  applications;
    }

    @Override
    public Application getById(Long id) {
        if (applicationRepository.findById(id).isPresent()) {
            return applicationRepository.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public Application saveOrUpdate(Application application) {
        applicationRepository.save(application);
        return application;
    }

    @Override
    public void delete(Long id) {
        applicationRepository.deleteById(id);

    }

    @Override
    public Application saveOrUpdateApplicationForm(ApplicationForm applicationForm) {
        Application savedApplication = saveOrUpdate(applicationFormToapplication.convert(applicationForm));

        System.out.println("Saved application Id: " + savedApplication.getId());
        return savedApplication;
    }
}