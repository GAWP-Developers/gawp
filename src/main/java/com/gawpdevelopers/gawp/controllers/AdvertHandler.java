package com.gawpdevelopers.gawp.controllers;

import com.gawpdevelopers.gawp.commands.AdvertForm;
import com.gawpdevelopers.gawp.converters.AdvertToAdvertForm;
import com.gawpdevelopers.gawp.domain.Advert;
import com.gawpdevelopers.gawp.domain.Mail;
import com.gawpdevelopers.gawp.domain.UserDetailsImpl;
import com.gawpdevelopers.gawp.services.AdvertService;
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
    public void setAdvertService(AdvertService advertService) {
        this.advertService = advertService;
    }

//    @RequestMapping({"/", "/index"})
//    public String redirToIndex(){
//        return "/index";
//    }

    //Mail yollamaca brom
    @RequestMapping({"/sendmail"})
    public String sendmail(){
        Mail mail = new Mail();
        mail.setFrom("noreply@gawp.com");
        mail.setTo("sebahattinyavuzkurt@std.iyte.edu.tr");
        mail.setSubject("Size Spring ilen Salamlar getirmişem");
        mail.setContent("Azerbaycandan gucak dolusu salamlar");
        emailService.sendSimpleMessage(mail);
        return "index";
    }

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
    public String saveOrUpdateAdvert(@Valid AdvertForm advertForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "advert/advertform";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl  user =  (UserDetailsImpl) userDetailsService.loadUserByUsername(auth.getName());
        advertForm.setGradID(user.getId());
        Advert savedAdvert = advertService.saveOrUpdateAdvertForm(advertForm);

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
        //TODO get adverts belonging to department and put it in model.
        //TODO html is currently hardcoded. Connect the front-end with back-end.
        List adverts = advertService.listAll(); //  TODO may not want to list all, only adverts belonging to department

        model.addAttribute("adverts", adverts);
        return "department/all";
    }

    @RequestMapping("/department/adverts/interviewNotSet")
    public String listAdvertsThatInterviewNotSet(Model model){
        List<Advert> allAdverts = advertService.listAll(); //  TODO may not want to list all, only adverts belonging to department

        // TODO CANA ÖZEL NOT: APPLICATION STATUSÜ UNUTMA!
        List<Advert> adverts =
                allAdverts.stream()
                        .filter(advert ->
                                advert.getApplications().stream()
                                        .anyMatch(application -> application.getInterview() == null))
                        .collect(Collectors.toList());

        model.addAttribute("adverts", adverts);

        return "department/all-1";
    }


    //  Applicant Mapping

    @RequestMapping({"/applicant/advert/", "/applicant/advert/list"})
    public String listAdvertsForApplicant(Model model){
        model.addAttribute("adverts", advertService.listAll());
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



    //


}
