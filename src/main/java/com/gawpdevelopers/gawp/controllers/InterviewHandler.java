package com.gawpdevelopers.gawp.controllers;

import com.gawpdevelopers.gawp.commands.InterviewForm;
import com.gawpdevelopers.gawp.converters.InterviewToInterviewForm;
import com.gawpdevelopers.gawp.domain.*;
import com.gawpdevelopers.gawp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    private InterviewToInterviewForm interviewToInterviewForm;

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

    public  void setInterviewToInterviewForm(InterviewToInterviewForm interviewToInterviewForm){
        this.interviewToInterviewForm = interviewToInterviewForm;
    }

    //  DepartmentMapping

    @RequestMapping("/department")
    public String departmentMainMenu(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl  user =  (UserDetailsImpl) userDetailsService.loadUserByUsername(auth.getName());
        System.out.println(String.join("  ", user.getfName(), user.getlName()));
        model.addAttribute("name", String.join("  ", user.getfName(), user.getlName()));
        model.addAttribute("deptName", user.getDepartmentType().toString());

        int applicationsToInterviewCount =
                applicationService.listAll().stream()
                        .filter(application -> application.getInterview() != null && application.getStatus() == ApplicationStatus.WAITINGFORINTERVIEW)
                        .filter (application -> application.getAdvert().getDepartmentType() == user.getDepartmentType())
                        .collect(Collectors.toList())
                        .size();
        model.addAttribute("applicationsToInterviewCount", applicationsToInterviewCount);

        int interviewedApplicationCount =
                applicationService.listAll().stream()
                        .filter(application -> application.getInterview() != null && application.getStatus() == ApplicationStatus.INTERVIEWED)
                        .filter(application -> application.getAdvert().getDepartmentType() == user.getDepartmentType())
                        .collect(Collectors.toList())
                        .size();
        model.addAttribute("interviewedApplicationCount", interviewedApplicationCount);

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

        Interview interview = new Interview();

        interview.setPlace(place);
        interview.setDate(date);
        interview.setComment(comment);
        interview.setPoint(point);
        interview.setApplication(applicationService.getById(application_id));

        return "redirect:/department/interview/list";
    }*/

    @RequestMapping("/department/interview/new/success/{successMessage}")
    public String interviewSuccess(Model model,
                                   @PathVariable String successMessage){
        if (successMessage.equals("interview-created"))
            return "department/succesful-interview";

        else if (successMessage.equals("interview-evaluated"))
            return "department/evaluated-interview";

        return "/department"; // TODO error page would be better.
    }

    @RequestMapping("/department/interview/new/{application_id}")
    public String newInterview(@PathVariable Long application_id, Model model){
        InterviewForm interviewForm = new InterviewForm();
        interviewForm.setApplication(applicationService.getById(application_id));
        model.addAttribute("interviewForm", interviewForm);
        model.addAttribute("fname", applicationService.getById(application_id).getApplicant().getfName());
        model.addAttribute("lname", applicationService.getById(application_id).getApplicant().getlName());
        model.addAttribute("advertName", applicationService.getById(application_id).getAdvert().getName());
        model.addAttribute("advertDeadline", applicationService.getById(application_id).getAdvert().getDeadlineDate());
        model.addAttribute("advertID", applicationService.getById(application_id).getAdvert().getId());

        //System.out.println("BURDAYIM");

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

    @RequestMapping(value = "/department/interview", method = RequestMethod.POST)
    public String saveOrUpdateInterview(@Valid InterviewForm interviewForm, BindingResult bindingResult,
                                        @RequestParam("sending-mail") String mailContent){

        if(bindingResult.hasErrors()){
            return "department/interviewform";
        }

        mailContent = mailContent.replaceAll("newLineBreak", "\n");
        //System.out.println(mailContent);

        sendMail(interviewForm, mailContent);

        interviewForm.getApplication().setStatus(ApplicationStatus.WAITINGFORINTERVIEW);
        Interview savedInterview = interviewService.saveOrUpdateInterviewForm(interviewForm);

        return "redirect:/department/interview/new/success/interview-created";
    }

    private void sendMail(InterviewForm interviewForm, String mailContent) {
        Mail mail = new Mail();
        mail.setFrom("noreply@gawp.com");
        mail.setTo(interviewForm.getApplication().getApplicant().getEmail());
        mail.setSubject("Interview Date/Time");
        mail.setContent(mailContent);
        emailService.sendSimpleMessage(mail);
    }

    @RequestMapping("/department/interview/delete/{id}")
    public String removeInterview(@PathVariable String id) {
        interviewService.delete(Long.valueOf(id));
        return "redirect:/department/interview/list";
    }

    @RequestMapping("/department/interview/do/{id}")
    public String doInterview(@PathVariable String id, Model model){

        Interview interview = interviewService.getById(Long.valueOf(id));
        model.addAttribute("interviewToReview",interview);

        Application application = applicationService.getById(interview.getApplication().getId());
        model.addAttribute("applicationToReview", application);

        System.out.println(interview.getId());

        System.out.println(application.getId());


        //  These are needed for applicant's foreign passport and permission letter parts
        //  in the html

        //  This is filtering with java streams.
        //  it basically searches for documents with passport and returns them in a list.
        //  If list is empty, isForeign = false.
        List passport = application.getDocuments().stream()
                .filter(d -> d.getDocType().toString().equals("PASSPORT"))
                .collect(Collectors.toList());
        boolean isForeign = !passport.isEmpty();
        model.addAttribute("isForeign", isForeign);

        //  Same logic as passport
        List permissionLetter = application.getDocuments().stream()
                .filter(d -> d.getDocType().toString().equals("PERMISSIONLETTER"))
                .collect(Collectors.toList());
        boolean isWorking = !permissionLetter.isEmpty();
        model.addAttribute("isWorking", isWorking);

        //  Same logic as passport
        List englishexam = application.getDocuments().stream()
                .filter(d -> d.getDocType().toString().equals("ENGLISHEXAM"))
                .collect(Collectors.toList());
        boolean hasEnglishExam = !englishexam.isEmpty();
        model.addAttribute("hasEnglishExam", hasEnglishExam);



        return "department/make-interview";

    }

    @RequestMapping("/department/interview/see/{id}")
    public String seeInterview(@PathVariable String id, Model model){
        Interview interview = interviewService.getById(Long.valueOf(id));
        System.out.println(interview == null);
        model.addAttribute("interviewToReview",interview);

        Application application = applicationService.getById(interview.getApplication().getId());
        model.addAttribute("applicationToReview", application);

        //  These are needed for applicant's foreign passport and permission letter parts
        //  in the html

        //  This is filtering with java streams.
        //  it basically searches for documents with passport and returns them in a list.
        //  If list is empty, isForeign = false.
        List passport = application.getDocuments().stream()
                .filter(d -> d.getDocType().toString().equals("PASSPORT"))
                .collect(Collectors.toList());
        boolean isForeign = !passport.isEmpty();
        model.addAttribute("isForeign", isForeign);

        //  Same logic as passport
        List permissionLetter = application.getDocuments().stream()
                .filter(d -> d.getDocType().toString().equals("PERMISSIONLETTER"))
                .collect(Collectors.toList());
        boolean isWorking = !permissionLetter.isEmpty();
        model.addAttribute("isWorking", isWorking);

        //  Same logic as passport
        List englishexam = application.getDocuments().stream()
                .filter(d -> d.getDocType().toString().equals("ENGLISHEXAM"))
                .collect(Collectors.toList());
        boolean hasEnglishExam = !englishexam.isEmpty();
        model.addAttribute("hasEnglishExam", hasEnglishExam);

        return "department/see-interview";
    }

    @RequestMapping(value = "/department/interview/afterreview", method = RequestMethod.POST)
    public String updateInterviewAfter(@Valid InterviewForm interviewForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "department/interviewform";
        }

        interviewForm.getApplication().setStatus(ApplicationStatus.INTERVIEWED);
        Interview savedInterview = interviewService.saveOrUpdateInterviewForm(interviewForm);

        System.out.println(savedInterview.getTime());

        return "redirect:/department/interview/new/success/interview-evaluated";
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
