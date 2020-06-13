package com.gawpdevelopers.gawp.services;

import com.gawpdevelopers.gawp.domain.Interview;

import java.util.List;

public interface InterviewService {
    List<Interview> listAll();

    Interview getById(Long id);

    Interview saveOrUpdate(Interview interview);

    void delete(Long id);
}
