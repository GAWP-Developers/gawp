package com.gawpdevelopers.gawp.converters;

import com.gawpdevelopers.gawp.commands.InterviewForm;
import com.gawpdevelopers.gawp.domain.Interview;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class InterviewToInterviewForm implements Converter<Interview, InterviewForm> {
    @Override
    public InterviewForm convert(Interview interview) {
        InterviewForm interviewForm = new InterviewForm();

        if (interview.getId() != null  && !StringUtils.isEmpty(interview.getId())) {
            interviewForm.setId(new Long(interview.getId()));
        }
        interviewForm.setPlace(interview.getPlace());
        interviewForm.setComment(interview.getComment());
        interviewForm.setApplication(interview.getApplication());
        interviewForm.setDate(interview.getDate());
        interviewForm.setPoint(interview.getPoint());
        interviewForm.setTime(interview.getTime());
        return interviewForm;
    }
}
