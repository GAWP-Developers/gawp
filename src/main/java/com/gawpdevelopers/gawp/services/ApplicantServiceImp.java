package com.gawpdevelopers.gawp.services;

import com.gawpdevelopers.gawp.commands.ApplicationForm;
import com.gawpdevelopers.gawp.domain.Applicant;
import com.gawpdevelopers.gawp.domain.Application;
import com.gawpdevelopers.gawp.repositories.ApplicantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ApplicantServiceImp implements ApplicantService{
    private ApplicantRepository applicantRepository;
    @Autowired
    public ApplicantServiceImp(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    @Override
    public List<Applicant> listAll() {
        List<Applicant> applicants = new ArrayList<>();
        applicantRepository.findAll().forEach(applicants::add); //fun with Java 8
        return applicants;

    }

    @Override
    public Applicant getById(Long id) {
        if (applicantRepository.findById(id).isPresent()) {
            return applicantRepository.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public Applicant getByApiId(String apid) {
        if (applicantRepository.findByApiID(apid).isPresent()) {
            return applicantRepository.findByApiID(apid).get();
        } else {
            return null;
        }
    }


    @Override
    public void delete(Long id) {
        applicantRepository.deleteById(id);
    }
    @Override
    public Applicant saveOrUpdate(Applicant applicant) {
        applicantRepository.save(applicant);
        return applicant;
    }


}
