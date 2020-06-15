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

import javax.print.Doc;
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

    private ApplicationToApplicationForm applicationToApplicationForm;
    private AdvertService advertService;
    private ApplicantService applicantService;
    private StorageService storageService;
    private DocumentService documentService;
    private MailService emailService;

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
//        System.out.println("Advert is null: " + advert == null);
//        model.addAttribute("target_advert", advert);
//        return "application/applicationform";
        return "applicant/new-application";
    }

    @RequestMapping("/applicant/application/new/success")
    public String applicationSuccess(){
        return "applicant/succesful-application";
    }

    @RequestMapping(value = "/application", method = RequestMethod.POST)
    public String saveOrUpdateApplication(@Valid ApplicationForm applicationForm, BindingResult bindingResult,
                                          @RequestParam("photo") MultipartFile photo,
                                          @RequestParam("transcript") MultipartFile transcript,
                                          @RequestParam("ales") MultipartFile ales,
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

//        return "redirect:/application/show/" + savedApplication.getId();
        return "redirect:/applicant/application/new/success";
    }

    @RequestMapping("/application/delete/{id}")
    public String delete(@PathVariable String id){
        applicationService.delete(Long.valueOf(id));
        return "redirect:/application/list";
    }

    @RequestMapping("/grad/applicationsBeforeForwarding")
    public String applicationsBeforeForwarding(){
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

        return "/grad/check-interview";
    }
    @RequestMapping("/setConfirm/{id}")
    public String setStatus(@PathVariable String id){
        Application application= applicationService.getById(Long.valueOf(id));
        application.setStatus(ApplicationStatus.CONFIRMED);
        Application saved = applicationService.saveOrUpdate(application);
        return "redirect:/grad/applicationsBeforeForwarding/preReview";

    }


    @RequestMapping(path="/ignore/{id}")
    public String decline(@PathVariable String id){

        System.out.println("Geliyorum");
        System.out.println("Rejected");
        Application application= applicationService.getById(Long.valueOf(id));
        application.setStatus(ApplicationStatus.REJECTED);
        Application saved = applicationService.saveOrUpdate(application);
        Applicant applicant =application.getApplicant();
        Mail mail = new Mail();
        System.out.println(mail.getContent());
        mail.setFrom("noreply@gawp.com");
        mail.setTo(applicant.getEmail());
        mail.setSubject("Size Spring ilen Salamlar getirmişem");
        mail.setContent("rejected");
        emailService.sendSimpleMessage(mail);
        return "redirect:/grad/applicationsBeforeForwarding/preReview";

    }



    @RequestMapping(path="/notify/{id}")
    public String notify(@PathVariable String id){
        //TODO notify için mail-message i fronttan alınması lazım
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


    @RequestMapping("/grad/applicationsBeforeForwarding/declined")
    public String listDeclinedApplications(Model model){
        //TODO List declined applications and add it as attribute to model

        return "/grad/declined-applications";
    }

    @RequestMapping("/grad/applicationsBeforeForwarding/verifiedAndApproved")
    public String listVerifiedAndApprovedApplications(Model model){
        //TODO List verified and approved applications and add it as attribute to model
        return "/grad/verfied-and-approved-applications";
    }

    @RequestMapping("/grad/applicationsBeforeForwarding/verify")
    public String listApplicationsToVerify(Model model){
        //TODO List approved applications and add it as attribute to model
        return "/grad/verify-approved-applications";
    }

    //  File Mapping

    @RequestMapping("/docs/{id}")
    public ResponseEntity<Resource> serveDocument(@PathVariable long id){
        Document doc = documentService.getById(id);

        // TODO Check whether the user should be able to access document
        // TODO For example, an applicant shouldn't see someone elses document

        Resource document = storageService.loadAsResource(doc);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + document.getFilename() + "\"").body(document);

    }


}
