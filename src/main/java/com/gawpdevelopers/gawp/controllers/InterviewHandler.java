package com.gawpdevelopers.gawp.controllers;

import com.gawpdevelopers.gawp.domain.Interview;
import com.gawpdevelopers.gawp.domain.InterviewInfo;
import com.gawpdevelopers.gawp.services.InterviewInfoService;
import com.gawpdevelopers.gawp.services.InterviewService;
import com.gawpdevelopers.gawp.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Controller class that responds to the /interview/* requests.
 */
public class InterviewHandler {

    private InterviewService interviewService;
    private InterviewInfoService interviewInfoService;
    private MailService emailService;


    @Autowired
    public void setInterviewService(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @Autowired
    public void setInterviewInfoService(InterviewInfoService interviewInfoService) {
        this.interviewInfoService = interviewInfoService;
    }

    @Autowired
    public void setEmailService(MailService emailService) {
        this.emailService = emailService;
    }


    @RequestMapping("/grad/interview/list")
    public String getInterviews(Model model) {
        model.addAttribute("interviews", interviewService.listAll());
        return "grad/find-interview-grad";
    }

    @RequestMapping("/grad/interview/show/{id}")
    public String getInterview(@PathVariable String id, Model model){
        model.addAttribute("interview", interviewService.getById(Long.valueOf(id)));
        return "interview/show";
    }

    @RequestMapping(value = "/interview", method = RequestMethod.POST)
    public String saveOrUpdateInterview(@Valid Interview interview, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "interview";
        }

        Interview savedInterview = interviewService.saveOrUpdate(interview);

        return "redirect:/grad/interview";
    }

    @RequestMapping("/grad/interview/delete/{id}")
    public String removeInterview(@PathVariable String id) {
        interviewService.delete(Long.valueOf(id));
        return "redirect:/grad/interview";
    }

    @RequestMapping("/grad/interviewInfo/list")
    public String getInterviewInfos(Model model) {
        model.addAttribute("interviewInfos", interviewInfoService.listAll());
        return "grad/find-interviewInfos-grad";
    }

    @RequestMapping("/grad/interviewInfo/show/{id}")
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

        return "redirect:/grad/interviewInfo";
    }

    @RequestMapping("/grad/interviewInfo/delete/{id}")
    public String removeInterviewInfo(@PathVariable String id) {
        interviewInfoService.delete(Long.valueOf(id));
        return "redirect:/grad/interviewInfo";
    }

}
