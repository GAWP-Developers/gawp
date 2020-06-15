package com.gawpdevelopers.gawp.controllers;

import com.gawpdevelopers.gawp.commands.AdvertForm;
import com.gawpdevelopers.gawp.commands.ApplicationForm;
import com.gawpdevelopers.gawp.commands.InterviewForm;
import com.gawpdevelopers.gawp.domain.*;
import com.gawpdevelopers.gawp.services.ApplicationService;
import com.gawpdevelopers.gawp.services.InterviewService;
import com.gawpdevelopers.gawp.services.MailService;
import com.gawpdevelopers.gawp.services.UserDetailsServiceImpl;
import com.gawpdevelopers.gawp.domain.Advert;
import com.gawpdevelopers.gawp.domain.Applicant;
import com.gawpdevelopers.gawp.domain.Interview;
import com.gawpdevelopers.gawp.domain.UserDetailsImpl;
import com.gawpdevelopers.gawp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;

/**
 * Controller class that responds to the /interview/* requests.
 */
@Controller
public class InterviewHandler {

    private InterviewService interviewService;
    //private InterviewInfoService interviewInfoService;
    private MailService emailService;
    private ApplicationService applicationService;
    private UserDetailsServiceImpl userDetailsService;
    private ApplicantService applicantService;

    @Autowired
    public void setApplicantService(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

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

    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    //  DepartmentMapping

    @RequestMapping("/department")
    public String departmentMainMenu(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl  user =  (UserDetailsImpl) userDetailsService.loadUserByUsername(auth.getName());
        System.out.println(String.join("  ", user.getfName(), user.getlName()));
        model.addAttribute("name", String.join("  ", user.getfName(), user.getlName()));

        //TODO Front End Integration is not completed
        //TODO application numbers are currently hardcoded.

        return "department/dept-main";
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

    /*
    @RequestMapping("/department/interview/new")
    public String newInterview(Model model){
        model.addAttribute("interviewForm", new InterviewForm());
//        return "advert/advertform";
        return "department/add-new-interview";
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
    }*/

    @RequestMapping("/department/interview/new/success")
    public String interviewSuccess(){
        return "department/succesful-interview";
    }

    @RequestMapping("/department/interview/new/{application_id}")
    public String newInterview(@PathVariable Long application_id, Model model){
        InterviewForm interviewForm = new InterviewForm();
        interviewForm.setApplication(applicationService.getById(application_id));
        model.addAttribute("interviewForm", interviewForm);
        model.addAttribute("fname", applicationService.getById(application_id).getApplicant().getfName());
        model.addAttribute("lname", applicationService.getById(application_id).getApplicant().getlName());

        /**System.out.println("application");

        System.out.println(interviewForm.getApplication().getId());

        System.out.println(interviewForm.getId());
        System.out.println(25);*/

        System.out.println(interviewForm.getTime());
        System.out.println(22);

//        System.out.println("Advert is null: " + advert == null);
//        model.addAttribute("target_advert", advert);
//        return "application/applicationform";
        //return "department/add-new-interview";
        return "department/interview-invite";
    }

    @RequestMapping(value = "/interview", method = RequestMethod.POST)
    public String saveOrUpdateInterview(@Valid InterviewForm interviewForm, BindingResult bindingResult){
        System.out.println(222);

        if(bindingResult.hasErrors()){
            return "interview/interviewform";
        }
        System.out.println(interviewForm.getTime());
        System.out.println(23);
        Interview savedInterview = interviewService.saveOrUpdateInterviewForm(interviewForm);
        System.out.println(savedInterview.getTime());
        System.out.println(24);
        return "redirect:/department/interview/new/success";
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
