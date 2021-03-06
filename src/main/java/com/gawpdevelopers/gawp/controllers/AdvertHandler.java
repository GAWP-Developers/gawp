package com.gawpdevelopers.gawp.controllers;

import com.gawpdevelopers.gawp.commands.AdvertForm;
import com.gawpdevelopers.gawp.converters.AdvertToAdvertForm;
import com.gawpdevelopers.gawp.domain.Advert;
import com.gawpdevelopers.gawp.domain.Applicant;
import com.gawpdevelopers.gawp.domain.DepartmentType;
import com.gawpdevelopers.gawp.domain.Mail;
import com.gawpdevelopers.gawp.domain.UserDetailsImpl;
import com.gawpdevelopers.gawp.services.AdvertService;
import com.gawpdevelopers.gawp.services.ApplicantService;
import com.gawpdevelopers.gawp.services.MailService;
import com.gawpdevelopers.gawp.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class that responds to the /advert/* requests.
 */
@Controller
public class AdvertHandler {
    private AdvertService advertService;

    private AdvertToAdvertForm advertToAdvertForm;

    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private MailService emailService;
    private ApplicantService applicantService;

    public void setEmailService(MailService emailService){
        this.emailService = emailService;
    }
    @Autowired
    public void setAdvertToAdvertForm(AdvertToAdvertForm advertToadvertForm) {
        this.advertToAdvertForm = advertToadvertForm;
    }
    @Autowired
    public void setUserDetailsServiceImpl(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setAdvertService(AdvertService advertService) {
        this.advertService = advertService;
    }

    @Autowired
    public void setApplicantService(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    //    @RequestMapping({"/", "/index"})
//    public String redirToIndex(){
//        return "/index";
//    }

    /*//Mail yollamaca brom
    @RequestMapping({"/sendmail"})
    public String sendmail(){
        Mail mail = new Mail();
        mail.setFrom("noreply@gawp.com");
        mail.setTo("sebahattinyavuzkurt@std.iyte.edu.tr");
        mail.setSubject("Size Spring ilen Salamlar getirmişem");
        mail.setContent("Azerbaycandan gucak dolusu salamlar");
        emailService.sendSimpleMessage(mail);
        return "index";
    }*/

    //  GRAD Mappings

    @RequestMapping("/grad")
    public String gradMainMenu(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl  user =  (UserDetailsImpl) userDetailsService.loadUserByUsername(auth.getName());
        System.out.println(String.join("  ", user.getfName(), user.getlName()));
        model.addAttribute("name", String.join("  ", user.getfName(), user.getlName()));
        return "grad/main-page-grad";
    }

    @RequestMapping("/grad/advert")
    public String addModifyAdvert(){
        return "grad/add-modify-adverts";
    }

    @RequestMapping("/grad/advert/new")
    public String newAdvert(Model model){
        model.addAttribute("advertForm", new AdvertForm());
//        return "advert/advertform";
        return "grad/add-new-advert";
    }

    @RequestMapping("/grad/advert/list")
    public String listAdverts(Model model){
        model.addAttribute("adverts", advertService.listAll());
        return "grad/find-advert-grad";
    }

    @RequestMapping(value = "/advert", method = RequestMethod.POST)
    public String saveOrUpdateAdvert(@Valid Advert advertForm, BindingResult bindingResult){
//        if(bindingResult.hasErrors()){
//            return "advert/advertform";
//        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl  user =  (UserDetailsImpl) userDetailsService.loadUserByUsername(auth.getName());
        advertForm.setGradID(user.getId());
        Advert savedAdvert = advertService.saveOrUpdate(advertForm);
        System.out.println(user.getDepartmentType());
        System.out.println(savedAdvert.getDepartmentType());

        return "redirect:/grad/advert";
    }

    @RequestMapping("/grad/advert/delete/{id}")
    public String delete(@PathVariable String id){
        advertService.delete(Long.valueOf(id));
        return "redirect:/grad/advert";
    }

    @RequestMapping("/grad/advert/edit/{id}")
    public String edit(@PathVariable String id, Model model){
        Advert advert = advertService.getById(Long.valueOf(id));
        AdvertForm advertForm = advertToAdvertForm.convert(advert);

        model.addAttribute("advertForm", advertForm);
        return "grad/add-new-advert";
    }

    //  Department Mapping

    @RequestMapping("/department/adverts")
    public String listAdvertsOfDepartment(Model model){
        //TODO html is currently hardcoded. Connect the front-end with back-end.
        List adverts = advertService.listAll();
        List<Advert> deptAdverts = getDepartmentAdverts(adverts);

        model.addAttribute("adverts", deptAdverts);
        return "department/all";
    }

    @RequestMapping("/department/adverts/interviewNotSet")
    public String listAdvertsThatInterviewNotSet(Model model){
        List<Advert> allAdverts = advertService.listAll();

        // TODO CANA ÖZEL NOT: APPLICATION STATUSÜ UNUTMA!
        List<Advert> adverts =
                allAdverts.stream()
                        .filter(advert ->
                                advert.getApplications().stream()
                                        .anyMatch(application -> application.getInterview() == null))
                        .collect(Collectors.toList());

        List<Advert> deptAdverts = getDepartmentAdverts(adverts);

        model.addAttribute("adverts", deptAdverts);

        return "department/all-1";
    }


    //  Applicant Mapping

    @RequestMapping({"/applicant/advert/", "/applicant/advert/list"})
    public String listAdvertsForApplicant(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Applicant applicant = applicantService.getByApiId(auth.getName());
        List adverts =
                advertService.listAll().stream()
                        .filter(advert ->
                                advert.getApplications().stream()
                                        .noneMatch(application -> application.getApplicant().getId() == applicant.getId()))
                        .collect(Collectors.toList());
        model.addAttribute("adverts", adverts);
        return "applicant/find-advert-applicant";
    }



    @RequestMapping("/applicant/advert/show/{id}")
    public String getAdverttoApplicant(@PathVariable String id, Model model){
        model.addAttribute("advert", advertService.getById(Long.valueOf(id)));
        return "applicant/advert-detail-applicant";
    }
    @RequestMapping( "/grad/advert/show/{id}")
    public String getAdverttoGrad(@PathVariable String id, Model model){
        model.addAttribute("advert", advertService.getById(Long.valueOf(id)));
        return "grad/advert-details";
    }


    @RequestMapping( "/department/advert/show/{id}")
    public String getAdverttoDept(@PathVariable String id, Model model){
        model.addAttribute("advert", advertService.getById(Long.valueOf(id)));
        return "department/dept-advert-details";
    }

    private List<Advert> getDepartmentAdverts(List<Advert> allAdverts){
        List<Advert> deptAdverts = new ArrayList<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl  user =  (UserDetailsImpl) userDetailsService.loadUserByUsername(auth.getName());
        DepartmentType currentDepartmentType = user.getDepartmentType();
        //System.out.println(user.getUsername());
        //System.out.println(currentDepartmentType);
        for (int i = 0; i < allAdverts.size();i++){
            Advert advert = (Advert) allAdverts.get(i);
            if (currentDepartmentType == advert.getDepartmentType()){
                deptAdverts.add(advert);
                System.out.println(advert.getDepartmentType());
                System.out.println(advert.getName());
                System.out.println("içindeki");

            }
            System.out.println(advert.getName());
        }
        return deptAdverts;

    }




    //


}
