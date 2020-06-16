package com.gawpdevelopers.gawp.controllers;

import com.gawpdevelopers.gawp.commands.ApplicationForm;
import com.gawpdevelopers.gawp.commands.DocumentForm;
import com.gawpdevelopers.gawp.converters.ApplicationToApplicationForm;
import com.gawpdevelopers.gawp.domain.*;
import com.gawpdevelopers.gawp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Controller class that responds to the /application/* requests.
 */
@Controller
public class ApplicationHandler {
    private ApplicationService applicationService;
    private UserDetailsServiceImpl userDetailsService;
    private ApplicationToApplicationForm applicationToApplicationForm;
    private AdvertService advertService;
    private ApplicantService applicantService;
    private StorageService storageService;
    private DocumentService documentService;
    private MailService emailService;
    @Autowired
    public void setUserDetailsServiceImpl(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setEmailService(MailService emailService){
        this.emailService = emailService;
    }

    @Autowired
    public void setApplicationToApplicationForm(ApplicationToApplicationForm applicationToApplicationForm) {
        this.applicationToApplicationForm = applicationToApplicationForm;
    }
    @Autowired
    public void setApplicantService(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @Autowired
    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Autowired
    public void setAdvertService(AdvertService advertService) {
        this.advertService = advertService;
    }

    @Autowired
    public void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

    @Autowired
    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    //    @RequestMapping("/application")
//    public String redirToList(){
//        return "redirect:/application/list";
//    }

//    @RequestMapping("/applicant")
//    public String applicantMenu(Model model){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//
//        model.addAttribute("name",auth.getDetails() );
//        return "applicant/main-page-applicant";
//    }

    //  Applicant Mapping

    @RequestMapping("/applicant")
    public String applicantMainMenu(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Applicant applicant = applicantService.getByApiId(auth.getName());
        model.addAttribute("name", applicant.getUserName());

        return "applicant/main-page-applicant";
    }

    @RequestMapping({"/application/list", "/application"})
    public String listApplications(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Applicant applicant = applicantService.getByApiId(auth.getName());
        model.addAttribute("applications", applicationService.listAllByApplicant(applicant));
        return "application/list";
    }

    @RequestMapping("/application/show/{id}")
    public String getApplication(@PathVariable String id, Model model){
        Application application = applicationService.getById(Long.valueOf(id));
        model.addAttribute("applicationToShow", application);
        return "application/show";
    }

    @RequestMapping("application/edit/{id}")
    public String edit(@PathVariable String id, Model model){
        Application application = applicationService.getById(Long.valueOf(id));
        ApplicationForm applicationForm = applicationToApplicationForm.convert(application);

        model.addAttribute("ApplicationForm", applicationForm);
        return "application/applicationform";
    }

    @RequestMapping("/applicant/application/new/{advert_id}")
    public String newApplication(@PathVariable Long advert_id, Model model){

        ApplicationForm appForm = new ApplicationForm();
        appForm.setAdvert(advertService.getById(advert_id));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Applicant applicant = applicantService.getByApiId(auth.getName());
        appForm.setApplicant(applicant);

        model.addAttribute("applicationForm", appForm);
        /**System.out.println("232 app new");
        System.out.println(appForm.getId());*/
//        System.out.println("Advert is null: " + advert == null);
//        model.addAttribute("target_advert", advert);
//        return "application/applicationform";
        return "applicant/new-application";
    }

    @RequestMapping("/applicant/application/new/success")
    public String applicationSuccess(){
        return "applicant/succesful-application";
    }

    @RequestMapping(value = "/grad/notify/{id}", method = RequestMethod.POST)
    public String notifyApplicant(@Valid ApplicationForm applicationForm, BindingResult bindingResult,
                                  @PathVariable String id,
                                  @RequestParam("sending-mail") String mailContent){
        //TODO still not working cant invoke post
        if(bindingResult.hasErrors()){
            return "/grad/applications-to-pre-review";
        }
        //Application saved = applicationService.saveOrUpdate(application);
        //Applicant applicant = application.getApplicant();
       //
        Application application = applicationService.getById(Long.valueOf(id));
        application.setStatus(ApplicationStatus.MISSINGDOCUMENT);
        Application savedApplication = applicationService.saveOrUpdate(application);
        System.out.println(application.getId());
        Mail mail = new Mail();
        mail.setFrom("noreply@gawp.com");
        mail.setTo(application.getApplicant().getEmail());
        mail.setSubject("Information About Missing Documents");
        mail.setContent(mailContent);
        emailService.sendSimpleMessage(mail);
        return "redirect:/grad/applicationsBeforeForwarding/preReview";
    }
    /**
    @RequestMapping(value = "/notify", method = RequestMethod.GET)
    public String notifyApplicant(){
        return "redirect:/grad/applicationsBeforeForwarding/preReview";
    }*/
    @RequestMapping(value = "/application", method = RequestMethod.POST)
    public String saveOrUpdateApplication(@Valid ApplicationForm applicationForm, BindingResult bindingResult,
                                          @RequestParam("photo") MultipartFile photo,
                                          @RequestParam("transcript") MultipartFile transcript,
                                          @RequestParam("ales") MultipartFile ales,
                                          @RequestParam(value = "englishExam", required = false) MultipartFile englishExam,
                                          @RequestParam("referenceLetter") MultipartFile referenceLetter,
                                          @RequestParam(value = "permissionLetter", required = false) MultipartFile permissionLetter,
                                          @RequestParam(value = "passport", required = false) MultipartFile passport,
                                          @RequestParam(value = "masterTranscript", required = false) MultipartFile masterTranscript) {
        System.out.println("UPLOAD ZAMANI");
//        if(bindingResult.hasErrors()){
//            return "/applicant";
//        }

        applicationForm.setStatus(ApplicationStatus.WAITINGFORCONTROL);
        applicationForm.setLastUpdateDate(new Date());
        applicantService.saveOrUpdate(applicationForm.getApplicant());
        Application savedApplication = applicationService.saveOrUpdateApplicationForm(applicationForm);
        System.out.println(savedApplication.getStatus());

        Integer applicationId = Math.toIntExact(savedApplication.getId());

        //  For each file, create corresponding Document entry in the db.
        DocumentForm photoForm = new DocumentForm();
        photoForm.setDocType(DocumentType.PHOTO);
        photoForm.setApplication(savedApplication);
        photoForm.setPath(storageService.store(photo, applicationId).toString());
        documentService.saveOrUpdateDocumentForm(photoForm);

        DocumentForm transcriptForm = new DocumentForm();
        transcriptForm.setDocType(DocumentType.TRANSCRIPT);
        transcriptForm.setApplication(savedApplication);
        transcriptForm.setPath(storageService.store(transcript, applicationId).toString());
        documentService.saveOrUpdateDocumentForm(transcriptForm);

        DocumentForm alesForm = new DocumentForm();
        alesForm.setDocType(DocumentType.ALES);
        alesForm.setApplication(savedApplication);
        alesForm.setPath(storageService.store(ales, applicationId).toString());
        documentService.saveOrUpdateDocumentForm(alesForm);

        DocumentForm referenceLetterForm = new DocumentForm();
        referenceLetterForm.setDocType(DocumentType.REFERENCELETTER);
        referenceLetterForm.setApplication(savedApplication);
        referenceLetterForm.setPath(storageService.store(referenceLetter, applicationId).toString());
        documentService.saveOrUpdateDocumentForm(referenceLetterForm);

        if (englishExam != null && !englishExam.isEmpty()) {
            DocumentForm englishExamForm = new DocumentForm();
            englishExamForm.setDocType(DocumentType.ENGLISHEXAM);
            englishExamForm.setApplication(savedApplication);
            englishExamForm.setPath(storageService.store(englishExam, applicationId).toString());
            documentService.saveOrUpdateDocumentForm(englishExamForm);
        }

        if (permissionLetter != null && !permissionLetter.isEmpty()) {
            DocumentForm permissionLetterForm = new DocumentForm();
            permissionLetterForm.setDocType(DocumentType.PERMISSIONLETTER);
            permissionLetterForm.setApplication(savedApplication);
            permissionLetterForm.setPath(storageService.store(permissionLetter, applicationId).toString());
            documentService.saveOrUpdateDocumentForm(permissionLetterForm);
        }
        if (passport != null && !passport.isEmpty()) {
            DocumentForm passportForm = new DocumentForm();
            passportForm.setDocType(DocumentType.PASSPORT);
            passportForm.setApplication(savedApplication);
            passportForm.setPath(storageService.store(passport, applicationId).toString());
            documentService.saveOrUpdateDocumentForm(passportForm);
        }

        if (masterTranscript != null && !masterTranscript.isEmpty()) {
            DocumentForm masterTranscriptForm = new DocumentForm();
            masterTranscriptForm.setDocType(DocumentType.MASTERTRANSCRIPT);
            masterTranscriptForm.setApplication(savedApplication);
            masterTranscriptForm.setPath(storageService.store(masterTranscript, applicationId).toString());
            documentService.saveOrUpdateDocumentForm(masterTranscriptForm);
        }

        /**System.out.println("232 app update");
        System.out.println(applicationForm.getId());*/

//        return "redirect:/application/show/" + savedApplication.getId();
        return "redirect:/applicant/application/new/success";
    }

    @RequestMapping("/application/delete/{id}")
    public String delete(@PathVariable String id){
        applicationService.delete(Long.valueOf(id));
        return "redirect:/application/list";
    }

    //  Grad Mapping

    @RequestMapping("/grad/applicationsBeforeForwarding")
    public String applicationsBeforeForwarding(Model model){
        List<Application> prereviewList= applicationService.listByStatus(ApplicationStatus.WAITINGFORCONTROL);
        applicationService.listByStatus(ApplicationStatus.MISSINGDOCUMENT).forEach(prereviewList::add);
        model.addAttribute("prereview",prereviewList.size());
        List<Application> toverifyList= applicationService.listByStatus(ApplicationStatus.CONFIRMED);
        model.addAttribute("toverify",toverifyList.size());
        List<Application> declinedList= applicationService.listByStatus(ApplicationStatus.REJECTED);
        model.addAttribute("declined",declinedList.size());
        List<Application> verifiedList= applicationService.listByStatus(ApplicationStatus.VERIFIED);
        model.addAttribute("verified",verifiedList.size());
        return "/grad/application-before-forwarding-to-deparment";
    }

    @RequestMapping("/grad/applicationsBeforeForwarding/preReview")
    public String listPreReviewApplications(Model model){
        List<Application> applicationList= applicationService.listByStatus(ApplicationStatus.WAITINGFORCONTROL);
        applicationService.listByStatus(ApplicationStatus.MISSINGDOCUMENT).forEach(applicationList::add);
        model.addAttribute("applications",applicationList);

        return "/grad/applications-to-pre-review";

    }
    @RequestMapping("/grad/applicationsBeforeForwarding/preReview/{id}")
    public String reviewApplication(@PathVariable String id, Model model){
        Application application = applicationService.getById(Long.valueOf(id));
        model.addAttribute("applicationToReview", application);
        model.addAttribute("mail",new Mail());


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

        return "/grad/check-interview";
    }
    @RequestMapping("/grad/setConfirm/{id}")
    public String setStatus(@PathVariable String id){
        Application application= applicationService.getById(Long.valueOf(id));
        application.setStatus(ApplicationStatus.CONFIRMED);
        Application saved = applicationService.saveOrUpdate(application);
        return "redirect:/grad/applicationsBeforeForwarding/preReview";

    }


    @RequestMapping(path="/grad/ignore/{id}")
    public String ignore(@PathVariable String id){

        Application application= applicationService.getById(Long.valueOf(id));
        application.setStatus(ApplicationStatus.REJECTED);
        Application saved = applicationService.saveOrUpdate(application);
        Applicant applicant =application.getApplicant();
        Mail mail = new Mail();
        System.out.println(mail.getContent());
        mail.setFrom("noreply@gawp.com");
        mail.setTo(applicant.getEmail());
        mail.setSubject("Application Inform Mail");
        mail.setContent("rejected");
        emailService.sendSimpleMessage(mail);
        return "redirect:/grad/applicationsBeforeForwarding/preReview";

    }


/*
    @RequestMapping(path="/notify/{id}")
    public String notify(@PathVariable String id){
        Application application= applicationService.getById(Long.valueOf(id));
        application.setStatus(ApplicationStatus.MISSINGDOCUMENT);
        Application saved = applicationService.saveOrUpdate(application);
        Applicant applicant =application.getApplicant();
        Mail mail = new Mail();
        mail.setFrom("noreply@gawp.com");
        mail.setTo(applicant.getEmail());
        mail.setSubject("Size Spring ilen Salamlar getirmişem");
        mail.setContent("You have notified");
        emailService.sendSimpleMessage(mail);
        return "redirect:/grad/applicationsBeforeForwarding/preReview";

    }

*/
    @RequestMapping("/grad/applicationsBeforeForwarding/declined")
    public String listDeclinedApplication(Model model){
        List<Application> declinedList= applicationService.listByStatus(ApplicationStatus.REJECTED);
        model.addAttribute("declined",declinedList);

        return "/grad/declined-applications";
    }
    @RequestMapping("/grad/applicationsBeforeForwarding/declined/{id}")
    public String listDeclinedApplications(@PathVariable String id,Model model){
        Application application = applicationService.getById(Long.valueOf(id));
        model.addAttribute("declinedApplication", application);
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

        return "/grad/declined-application";
    }

    @RequestMapping("/grad/applicationsBeforeForwarding/verifiedAndApproved")
    public String verifiedAndApprovedApplications(Model model){
        List<Application> verifiedList= applicationService.listByStatus(ApplicationStatus.VERIFIED);
        model.addAttribute("verified",verifiedList);
        return "/grad/verified-and-approved-applications";
    }

    @RequestMapping("/grad/applicationsBeforeForwarding/verifiedAndApproved/{id}")
    public String declinedApplication(@PathVariable String id,Model model){
        Application application = applicationService.getById(Long.valueOf(id));
        model.addAttribute("verifiedApplication", application);
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
        return "/grad/verified-application";
    }

    @RequestMapping("/grad/applicationsBeforeForwarding/verify")
    public String listApplicationsToVerify(Model model){
        List<Application> toverifyList= applicationService.listByStatus(ApplicationStatus.CONFIRMED);
        model.addAttribute("toverify",toverifyList);
        return "/grad/verify-approved-applications";
    }
    @RequestMapping("/grad/applicationsBeforeForwarding/verify/{id}")
    public String applicationToVerify(@PathVariable String id,Model model){
        Application application = applicationService.getById(Long.valueOf(id));
        model.addAttribute("applicationToVerify", application);
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
        return "/grad/verify-application";
    }
    @RequestMapping(path="/grad/verify/{id}")
    public String verify(@PathVariable String id){

        Application application= applicationService.getById(Long.valueOf(id));
        application.setStatus(ApplicationStatus.VERIFIED);
        Application saved = applicationService.saveOrUpdate(application);

        return "redirect:/grad/applicationsBeforeForwarding/verify";

    }
    @RequestMapping(path="/grad/decline/{id}")
    public String decline(@PathVariable String id){

        Application application= applicationService.getById(Long.valueOf(id));
        application.setStatus(ApplicationStatus.REJECTED);
        Application saved = applicationService.saveOrUpdate(application);
        Mail mail = new Mail();
        System.out.println(mail.getContent());
        mail.setFrom("noreply@gawp.com");
        mail.setTo(saved.getApplicant().getEmail());
        mail.setSubject("Application Inform Mail");
        mail.setContent("rejected");
        emailService.sendSimpleMessage(mail);

        return "redirect:/grad/applicationsBeforeForwarding/verify";

    }

    // from department

    @RequestMapping(path="/grad/applicationsFromDepartment")
    public String applicationFromDept(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl  user =  (UserDetailsImpl) userDetailsService.loadUserByUsername(auth.getName());
        List<Advert> adverts = advertService.listByGradID(user.getId());
        model.addAttribute("adverts",adverts);


        return "grad/application-from-dept";

    }
    @RequestMapping(path="/grad/applicationsFromDepartment/see-applications/{id}")
    public String applicationsFromDept(@PathVariable String id, Model model){
        List<Application> applications = applicationService.listAllInterviewedByAdvert(advertService.getById(Long.valueOf(id)));
        model.addAttribute("interviewedApplications",applications);


        return "grad/see-applications";

    }
    //TODO html is waiting elman
    @RequestMapping(path="/grad/applicationsFromDepartment/see-application/{id}")
    public String applicationFromDept(@PathVariable String id, Model model){
        Application application = applicationService.getById(Long.valueOf(id));
        model.addAttribute("interviewedApplication",application);


        return "grad/see-application";

    }



    //  File Mapping

    @RequestMapping("/docs/{id}")
    public ResponseEntity<Resource> serveDocument(@PathVariable long id){
        Document doc = documentService.getById(id);

        /*
        TODO Check whether the user should be able to access document
        For example, an applicant shouldn't see someone elses document
        */
        Resource document = storageService.loadAsResource(doc);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + document.getFilename() + "\"").body(document);

    }

    //  Department Mapping

    @RequestMapping("/department/applicationsToInterview")
    public String listApplicationsToInterview(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl  user =  (UserDetailsImpl) userDetailsService.loadUserByUsername(auth.getName());

        List<Application> applications = applicationService.listAll();
        List<Application> applicationsToInterview =
                applications.stream()
                        .filter(application ->  application.getStatus() == ApplicationStatus.WAITINGFORINTERVIEW)
                        .filter(application -> application.getAdvert().getDepartmentType() == user.getDepartmentType())
                        .collect(Collectors.toList());

        model.addAttribute("applicationsToInterview", applicationsToInterview);

        return "department/applications-to-interview";
    }

    @RequestMapping("/department/interviewedApplications")
    public String listInterviewedApplications(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl  user =  (UserDetailsImpl) userDetailsService.loadUserByUsername(auth.getName());
        
        List<Application> applications = applicationService.listAll();
        List<Application> interviewedApplications =
                applications.stream()
                        .filter(application -> application.getStatus() == ApplicationStatus.INTERVIEWED)
                        .filter(application -> application.getStatus() != ApplicationStatus.ACCEPTED)
                        .filter(application -> application.getAdvert().getDepartmentType() == user.getDepartmentType())
                        .collect(Collectors.toList());

        model.addAttribute("interviewedApplications", interviewedApplications);

        return "department/interviewed-applications";

    }

    @RequestMapping("/department/adverts/interviewNotSet/applications/{advertId}")
    public String listInterviewNotSetApplications(Model model,
                                                  @PathVariable Long advertId){

        //Advert advert = advertService.getById(advertId);
        //  TODO CANA ÖZEL NOT: STATUSÜ UNUTMA!
        List<Application> allApplications = applicationService.listAll();
        List<Application> applications =
                allApplications.stream()
                        .filter(application -> application.getInterview() == null)
                        .filter(application -> application.getAdvert().getId().equals(advertId))
                        .collect(Collectors.toList());

        model.addAttribute("applications", applications);

        return "department/all-1-1";
    }


}
