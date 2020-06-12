package com.gawpdevelopers.gawp.converters;


import com.gawpdevelopers.gawp.commands.ApplicationForm;
import com.gawpdevelopers.gawp.domain.Application;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ApplicationFormToApplication implements Converter<ApplicationForm, Application> {


    @Override
    public Application convert(ApplicationForm applicationForm) {
        Application application = new Application();
        if (applicationForm.getId() != null  && !StringUtils.isEmpty(applicationForm.getId())) {
            application.setId(new Long(applicationForm.getId()));
        }

        application.setId(applicationForm.getId());
        application.setAdvert(applicationForm.getAdvert());
        application.setIntID(applicationForm.getIntID());
        application.setApplicant(applicationForm.getApplicant());
        application.setStatus(applicationForm.getStatus());
        application.setApplicationDegree(applicationForm.getApplicationDegree());
        application.setLastUpdateDate(applicationForm.getLastUpdateDate());
        System.out.println("advert id is" + applicationForm.getAdvert());

        return application;
    }

}
