package com.gawpdevelopers.gawp.converters;

import com.gawpdevelopers.gawp.commands.InterviewForm;
import com.gawpdevelopers.gawp.domain.Interview;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class InterviewFormToInterview implements Converter<InterviewForm, Interview> {

    @Override
    public Interview convert(InterviewForm interviewForm) {


        Interview interview = new Interview();
        if (interviewForm.getId() != null  && !StringUtils.isEmpty(interviewForm.getId())) {
            interview.setId(new Long(interviewForm.getId()));
        }
        interview.setPlace(interviewForm.getPlace());
        interview.setComment(interviewForm.getComment());
        interview.setApplication(interviewForm.getApplication());
        interview.setDate(interviewForm.getDate());
        interview.setPoint(interviewForm.getPoint());
        interview.setTime(interviewForm.getTime());
        interview.setInfo(null);
        return interview;
    }
}
