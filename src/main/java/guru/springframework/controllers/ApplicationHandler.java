package guru.springframework.controllers;

import guru.springframework.commands.ApplicationForm;
import guru.springframework.commands.DocumentForm;
import guru.springframework.converters.ApplicationToApplicationForm;
import guru.springframework.domain.*;
import guru.springframework.services.AdvertService;
import guru.springframework.services.ApplicationService;
import guru.springframework.services.DocumentService;
import guru.springframework.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;

/**
 * Controller class that responds to the /application/* requests.
 */
@Controller
public class ApplicationHandler {
    private ApplicationService applicationService;

    private ApplicationToApplicationForm applicationToApplicationForm;
    private AdvertService advertService;
    private StorageService storageService;
    private DocumentService documentService;

    @Autowired
    public void setApplicationToApplicationForm(ApplicationToApplicationForm applicationToApplicationForm) {
        this.applicationToApplicationForm = applicationToApplicationForm;
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

    @RequestMapping({"/application/list", "/application"})
    public String listApplications(Model model){
        System.out.println("AGAM GİREMEDİM");
        model.addAttribute("applications", applicationService.listAll());
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

    @RequestMapping("/application/new/{advert_id}")
    public String newApplication(@PathVariable Long advert_id, Model model){
        System.out.println("GELDİM ÇOK YAKINIM");
        ApplicationForm appForm = new ApplicationForm();
        appForm.setAdvert(advertService.getById(advert_id));
        model.addAttribute("applicationForm", appForm);
//        System.out.println("Advert is null: " + advert == null);
//        model.addAttribute("target_advert", advert);
        return "application/applicationform";
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
        if(bindingResult.hasErrors()){
            return "application/applicationform";
        }

        applicationForm.setStatus(ApplicationStatus.WAITINGFORCONTROL);
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

        DocumentForm permissionLetterForm = new DocumentForm();
        photoForm.setDocType(DocumentType.PERMISSIONLETTER);
        photoForm.setApplication(savedApplication);
        photoForm.setPath(storageService.store(permissionLetter).toString());
        documentService.saveOrUpdateDocumentForm(permissionLetterForm);

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

//        return "redirect:/application/show/" + savedApplication.getId();
        return "redirect:/application/list";
    }

    @RequestMapping("/application/delete/{id}")
    public String delete(@PathVariable String id){
        applicationService.delete(Long.valueOf(id));
        return "redirect:/application/list";
    }
}
