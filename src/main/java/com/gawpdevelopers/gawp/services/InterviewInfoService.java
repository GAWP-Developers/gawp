package com.gawpdevelopers.gawp.services;
import com.gawpdevelopers.gawp.domain.InterviewInfo;

import java.util.List;

public interface InterviewInfoService {
    List<InterviewInfo> listAll();

    InterviewInfo getById(Long id);

    InterviewInfo saveOrUpdate(InterviewInfo interviewInfo);

    void delete(Long id);
}
