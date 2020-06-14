package com.gawpdevelopers.gawp.controllers;

import com.gawpdevelopers.gawp.domain.*;
import com.gawpdevelopers.gawp.services.*;
import com.gawpdevelopers.gawp.commands.ApplicationForm;
import com.gawpdevelopers.gawp.commands.DocumentForm;
import com.gawpdevelopers.gawp.converters.ApplicationToApplicationForm;
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
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.util.List;



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
        System.out.println("GELDİM ÇOK YAKINIM");
        ApplicationForm appForm = new ApplicationForm();
        appForm.setAdvert(advertService.getById(advert_id));
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

    @RequestMapping(value = "/application", method = RequestMethod.POST)
    public String saveOrUpdateApplication(@Valid ApplicationForm applicationForm, BindingResult bindingResult,
                                          @RequestParam("photo") MultipartFile photo,
                                          @RequestParam("transcript") MultipartFile transcript,
                                          @RequestParam("ales") MultipartFile ales,
                                          @RequestParam("referenceLetter") MultipartFile referenceLetter,
                                          @RequestParam("permissionLetter") MultipartFile permissionLetter,
                                          @RequestParam("passport") MultipartFile passport,
                                          @RequestParam("masterTranscript") MultipartFile masterTranscript) {
        System.out.println("UPLOAD ZAMANI");
        if(bindingResult.hasErrors()){
            return "/applicant";
        }

        applicationForm.setStatus(ApplicationStatus.WAITINGFORCONTROL);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Applicant applicant = applicantService.getByApiId(auth.getName());
        applicationForm.setApplicant(applicant);
        Application savedApplication = applicationService.saveOrUpdateApplicationForm(applicationForm);
        System.out.println(savedApplication.getStatus());

        //  For each file, create corresponding Document entry in the db.
        DocumentForm photoForm = new DocumentForm();
        photoForm.setDocType(DocumentType.PHOTO);
        photoForm.setApplication(savedApplication);
        photoForm.setPath(storageService.store(photo).toString());
        documentService.saveOrUpdateDocumentForm(photoForm);

        DocumentForm transcriptForm = new DocumentForm();
        photoForm.setDocType(DocumentType.TRANSCRIPT);
        photoForm.setApplication(savedApplication);
        photoForm.setPath(storageService.store(transcript).toString());
        documentService.saveOrUpdateDocumentForm(transcriptForm);

        DocumentForm alesForm = new DocumentForm();
        photoForm.setDocType(DocumentType.ALES);
        photoForm.setApplication(savedApplication);
        photoForm.setPath(storageService.store(ales).toString());
        documentService.saveOrUpdateDocumentForm(alesForm);

        DocumentForm referenceLetterForm = new DocumentForm();
        photoForm.setDocType(DocumentType.REFERENCELETTER);
        photoForm.setApplication(savedApplication);
        photoForm.setPath(storageService.store(referenceLetter).toString());
        documentService.saveOrUpdateDocumentForm(referenceLetterForm);
        //TODO what if applicant is not working, no need to upload this file
        DocumentForm permissionLetterForm = new DocumentForm();
        photoForm.setDocType(DocumentType.PERMISSIONLETTER);
        photoForm.setApplication(savedApplication);
        photoForm.setPath(storageService.store(permissionLetter).toString());
        documentService.saveOrUpdateDocumentForm(permissionLetterForm);
        //TODO what if applicant has turkish id card, no need to upload this file
        DocumentForm passportForm = new DocumentForm();
        photoForm.setDocType(DocumentType.PASSPORT);
        photoForm.setApplication(savedApplication);
        photoForm.setPath(storageService.store(passport).toString());
        documentService.saveOrUpdateDocumentForm(passportForm);

        DocumentForm masterTranscriptForm = new DocumentForm();
        photoForm.setDocType(DocumentType.MASTERTRANSCRIPT);
        photoForm.setApplication(savedApplication);
        photoForm.setPath(storageService.store(masterTranscript).toString());
        documentService.saveOrUpdateDocumentForm(masterTranscriptForm);

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
    public String applicationsBeforeForwarding(){
        return "/grad/application-before-forwarding-to-deparment";
    }

    @RequestMapping("/grad/applicationsBeforeForwarding/preReview")
    public String listPreReviewApplications(Model model){
        List<Application> applicationList= applicationService.listByStatus(ApplicationStatus.WAITINGFORCONTROL);
        model.addAttribute("applications",applicationList);

        return "/grad/applications-to-pre-review";

    }

    @RequestMapping("/grad/applicationsBeforeForwarding/declined")
    public String listDeclinedApplications(Model model){
        //TODO List declined applications and add it as attribute to model (DONE)
        List<Application> declinedApplications = applicationService.listByStatus(ApplicationStatus.REJECTED);
        model.addAttribute("declinedApplications", declinedApplications);

        return "/grad/declined-applications";
    }

    @RequestMapping("/grad/applicationsBeforeForwarding/verifiedAndApproved")
    public String listVerifiedAndApprovedApplications(Model model){
        //TODO List verified and approved applications and add it as attribute to model
        return "/grad/verified-and-approved-applications";
    }

    @RequestMapping("/grad/applicationsBeforeForwarding/verify")
    public String listApplicationsToVerify(Model model){
        //TODO List approved applications and add it as attribute to model
        return "/grad/verify-approved-applications";
    }

    //  Department Mapping

    @RequestMapping("/department/applicationsToInterview")
    public String listApplicationsToReview(){
        //TODO  Front End Integration
        return "TO BE IMPLEMENTED";
    }

    @RequestMapping("/department/interviewedApplications")
    public String listInterviewedApplication(){
        //TODO Front End Integration
        return "TO BE IMPLEMENTED";
    }


}
