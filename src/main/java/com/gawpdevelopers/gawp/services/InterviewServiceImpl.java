package com.gawpdevelopers.gawp.services;

import com.gawpdevelopers.gawp.domain.Interview;
import com.gawpdevelopers.gawp.repositories.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InterviewServiceImpl implements InterviewService{

    private InterviewRepository interviewRepository;

    @Autowired
    public InterviewServiceImpl(InterviewRepository interviewRepository) {
        this.interviewRepository = interviewRepository;
    }

    @Override
    public List<Interview> listAll() {
        List<Interview> interviews = new ArrayList<>();
        interviewRepository.findAll().forEach(interviews::add); //fun with Java 8
        return interviews;
    }

    @Override
    public Interview getById(Long id) {
        if (interviewRepository.findById(id).isPresent()) {
            return interviewRepository.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public Interview saveOrUpdate(Interview interview) {
        interviewRepository.save(interview);
        return interview;
    }

    @Override
    public void delete(Long id) {
        interviewRepository.deleteById(id);

    }
}
