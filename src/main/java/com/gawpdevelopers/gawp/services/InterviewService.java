package com.gawpdevelopers.gawp.services;

import com.gawpdevelopers.gawp.commands.AdvertForm;
import com.gawpdevelopers.gawp.domain.Advert;
import com.gawpdevelopers.gawp.domain.Interview;
import com.gawpdevelopers.gawp.commands.InterviewForm;


import java.util.List;

public interface InterviewService {
    List<Interview> listAll();

    Interview getById(Long id);

    Interview saveOrUpdate(Interview interview);

    void delete(Long id);

    Interview saveOrUpdateInterviewForm(InterviewForm interviewFrom);

}
