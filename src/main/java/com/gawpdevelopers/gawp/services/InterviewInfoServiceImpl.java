package com.gawpdevelopers.gawp.services;

import com.gawpdevelopers.gawp.domain.InterviewInfo;
import com.gawpdevelopers.gawp.repositories.InterviewInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InterviewInfoServiceImpl implements InterviewInfoService {
    private InterviewInfoRepository interviewInfoRepository;

    @Autowired
    public InterviewInfoServiceImpl(InterviewInfoRepository interviewInfoRepository) {
        this.interviewInfoRepository = interviewInfoRepository;
    }

    @Override
    public List<InterviewInfo> listAll() {
        List<InterviewInfo> interviewInfos = new ArrayList<>();
        interviewInfoRepository.findAll().forEach(interviewInfos::add); //fun with Java 8
        return interviewInfos;
    }

    @Override
    public InterviewInfo getById(Long id) {
        if (interviewInfoRepository.findById(id).isPresent()) {
            return interviewInfoRepository.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public InterviewInfo saveOrUpdate(InterviewInfo interviewInfo) {
        interviewInfoRepository.save(interviewInfo);
        return interviewInfo;
    }

    @Override
    public void delete(Long id) {
        interviewInfoRepository.deleteById(id);

    }
}
