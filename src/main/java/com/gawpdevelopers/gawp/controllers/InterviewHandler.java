package com.gawpdevelopers.gawp.controllers;

import com.gawpdevelopers.gawp.domain.Interview;
import com.gawpdevelopers.gawp.services.ApplicationService;
import com.gawpdevelopers.gawp.services.InterviewService;
import com.gawpdevelopers.gawp.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Controller class that responds to the /interview/* requests.
 */
public class InterviewHandler {

    private InterviewService interviewService;
    //private InterviewInfoService interviewInfoService;
    private MailService emailService;
    private ApplicationService applicationService;


    @Autowired
    public void setInterviewService(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @Autowired
    public void setEmailService(MailService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @RequestMapping("/department/interview/list")
    public String getInterviews(Model model) {
        model.addAttribute("interviews", interviewService.listAll());
        return "department/find-interview-department";
    }

    @RequestMapping("/department/interview/show/{id}")
    public String getInterview(@PathVariable String id, Model model){
        model.addAttribute("interview", interviewService.getById(Long.valueOf(id)));
        return "interview/show";
    }

    @RequestMapping(value = "/interview", method = RequestMethod.POST)
    public String saveOrUpdateInterview(BindingResult bindingResult,
                                        @RequestParam("place") String place,
                                        @RequestParam("date") Date date,
                                        @RequestParam("comment") String comment,
                                        @RequestParam("point") Double point,
                                        @PathVariable Long application_id) {
        if(bindingResult.hasErrors())
            return "interview";

        // TODO things to do before setting parameters?
        // TODO @Valid Interview interview (as parameter)
        Interview interview = new Interview();

        interview.setPlace(place);
        interview.setDate(date);
        interview.setComment(comment);
        interview.setPoint(point);
        interview.setApplication(applicationService.getById(application_id));

        return "redirect:/department/interview/list";
    }

    @RequestMapping("/department/interview/delete/{id}")
    public String removeInterview(@PathVariable String id) {
        interviewService.delete(Long.valueOf(id));
        return "redirect:/department/interview/list";
    }
    /*

                INTERVIEWINFO PART
    @Autowired
    public void setInterviewInfoService(InterviewInfoService interviewInfoService) {
        this.interviewInfoService = interviewInfoService;
    }



    @RequestMapping("/department/interviewInfo/list")
    public String getInterviewInfos(Model model) {
        model.addAttribute("interviewInfos", interviewInfoService.listAll());
        return "department/find-interviewInfos-department";
    }

    @RequestMapping("/department/interviewInfo/show/{id}")
    public String getInterviewInfo(@PathVariable String id, Model model){
        model.addAttribute("interviewInfo", interviewInfoService.getById(Long.valueOf(id)));
        return "interviewInfo/show";
    }


    @RequestMapping(value = "/interviewInfo", method = RequestMethod.POST)
    public String saveOrUpdateInterviewInfos(@Valid InterviewInfo interviewInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "interviewInfo";
        }

        InterviewInfo savedInterview = interviewInfoService.saveOrUpdate(interviewInfo);

        return "redirect:/department/interviewInfo";
    }

    @RequestMapping("/department/interviewInfo/delete/{id}")
    public String removeInterviewInfo(@PathVariable String id) {
        interviewInfoService.delete(Long.valueOf(id));
        return "redirect:/department/interviewInfo";
    }
    */
}
