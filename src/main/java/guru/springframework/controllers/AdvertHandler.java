package guru.springframework.controllers;

import guru.springframework.commands.AdvertForm;
import guru.springframework.converters.AdvertToAdvertForm;
import guru.springframework.domain.Advert;
import guru.springframework.services.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by jt on 1/10/17.
 */
@Controller
public class AdvertHandler {
    private AdvertService advertService;

    private AdvertToAdvertForm advertToAdvertForm;

    @Autowired
    public void setAdvertToAdvertForm(AdvertToAdvertForm advertToadvertForm) {
        this.advertToAdvertForm = advertToadvertForm;
    }

    @Autowired
    public void setAdvertService(AdvertService advertService) {
        this.advertService = advertService;
    }

    @RequestMapping({"/", "/index"})
    public String redirToIndex(){
        return "/index";
    }

    @RequestMapping("/advert/list")
    public String listAdverts(Model model){
        model.addAttribute("adverts", advertService.listAll());
        return "advert/grad/find-advert-grad";
//        return "advert/list";
//        return "advert/add-new-advert";
    }

    @RequestMapping({"/advert/applicant", "/advert/applicant/list"})
    public String listAdvertsForApplicant(Model model){
        model.addAttribute("adverts", advertService.listAll());
        return "advert/applicant/find-advert-applicant";
    }


    @RequestMapping("/advert/show/{id}")
    public String getAdvert(@PathVariable String id, Model model){
        model.addAttribute("advert", advertService.getById(Long.valueOf(id)));
        return "advert/show";
    }

    @RequestMapping("advert/edit/{id}")
    public String edit(@PathVariable String id, Model model){
        Advert advert = advertService.getById(Long.valueOf(id));
        AdvertForm advertForm = advertToAdvertForm.convert(advert);

        model.addAttribute("advertForm", advertForm);
        return "advert/add-new-advert";
    }

    @RequestMapping("/advert")
    public String addModifyAdvert(){
        return "advert/add-modify-adverts";
    }

    @RequestMapping("/advert/new")
    public String newAdvert(Model model){
        model.addAttribute("advertForm", new AdvertForm());
//        return "advert/advertform";
        return "advert/add-new-advert";
    }

    @RequestMapping(value = "/advert", method = RequestMethod.POST)
    public String saveOrUpdateAdvert(@Valid AdvertForm advertForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "advert/advertform";
        }

        Advert savedAdvert = advertService.saveOrUpdateAdvertForm(advertForm);

//        return "redirect:/advert/show/" + savedAdvert.getId();
        return "redirect:/advert";
    }

    @RequestMapping("/advert/delete/{id}")
    public String delete(@PathVariable String id){
        advertService.delete(Long.valueOf(id));
        return "redirect:/advert/list";
    }
}
