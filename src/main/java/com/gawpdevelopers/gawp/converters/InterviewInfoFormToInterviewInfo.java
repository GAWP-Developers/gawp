package com.gawpdevelopers.gawp.converters;

import com.gawpdevelopers.gawp.commands.AdvertForm;
import com.gawpdevelopers.gawp.commands.InterviewInfoForm;
import com.gawpdevelopers.gawp.domain.Advert;
import com.gawpdevelopers.gawp.domain.InterviewInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class InterviewInfoFormToInterviewInfo implements Converter<InterviewInfoForm, InterviewInfo> {

    @Override
    public InterviewInfo convert(InterviewInfoForm interviewInfoForm) {
        InterviewInfo interviewInfo = new InterviewInfo();
        if (interviewInfoForm.getId() != null  && !StringUtils.isEmpty(interviewInfoForm.getId())) {
            interviewInfo.setId(new Long(interviewInfoForm.getId()));
        }

        interviewInfo.setStartDate(interviewInfoForm.getStartDate());
        interviewInfo.setEndDate(interviewInfoForm.getEndDate());
        interviewInfo.setNotifyDate(interviewInfoForm.getNotifyDate());
        interviewInfo.setStartTime(interviewInfoForm.getStartTime());
        interviewInfo.setEndTime(interviewInfoForm.getEndTime());
        interviewInfo.setTotalInterview(interviewInfoForm.getTotalInterview());
        interviewInfo.setTimePerInt(interviewInfoForm.getTimePerInt());
        interviewInfo.setDays(interviewInfoForm.getDays());
        interviewInfo.setNumOfIntPerDay(interviewInfoForm.getNumOfIntPerDay());
        interviewInfo.setTimeBetweenInt(interviewInfoForm.getTimeBetweenInt());
        interviewInfo.setAnnounced(interviewInfoForm.getAnnounced());
        interviewInfo.setAdvert(interviewInfoForm.getAdvert());
        interviewInfo.setInterviews(interviewInfoForm.getInterviews());
        interviewInfo.setMailTemplate(interviewInfoForm.getMailTemplate());
        interviewInfo.setLunchStartTime(interviewInfoForm.getLunchStartTime());
        interviewInfo.setLunchEndTime(interviewInfoForm.getLunchEndTime());

        return interviewInfo;
    }
}
