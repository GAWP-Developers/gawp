package com.gawpdevelopers.gawp.converters;

import com.gawpdevelopers.gawp.commands.InterviewInfoForm;
import com.gawpdevelopers.gawp.domain.InterviewInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InterviewInfoToInterviewInfoForm implements Converter<InterviewInfo, InterviewInfoForm> {

    @Override
    public InterviewInfoForm convert(InterviewInfo interviewInfo) {
        InterviewInfoForm interviewInfoForm = new InterviewInfoForm();

        interviewInfoForm.setId(interviewInfo.getId());
        interviewInfoForm.setStartDate(interviewInfo.getStartDate());
        interviewInfoForm.setEndDate(interviewInfo.getEndDate());
        interviewInfoForm.setNotifyDate(interviewInfo.getNotifyDate());
        interviewInfoForm.setStartTime(interviewInfo.getStartTime());
        interviewInfoForm.setEndTime(interviewInfo.getEndTime());
        interviewInfoForm.setTotalInterview(interviewInfo.getTotalInterview());
        interviewInfoForm.setTimePerInt(interviewInfo.getTimePerInt());
        interviewInfoForm.setDays(interviewInfo.getDays());
        interviewInfoForm.setNumOfIntPerDay(interviewInfo.getNumOfIntPerDay());
        interviewInfoForm.setTimeBetweenInt(interviewInfo.getTimeBetweenInt());
        interviewInfoForm.setAnnounced(interviewInfo.getAnnounced());
        interviewInfoForm.setAdvert(interviewInfo.getAdvert());
        interviewInfoForm.setInterviews(interviewInfo.getInterviews());
        interviewInfoForm.setMailTemplate(interviewInfo.getMailTemplate());
        interviewInfoForm.setLunchStartTime(interviewInfo.getLunchStartTime());
        interviewInfoForm.setLunchEndTime(interviewInfo.getLunchEndTime());


        return interviewInfoForm;
    }
}
