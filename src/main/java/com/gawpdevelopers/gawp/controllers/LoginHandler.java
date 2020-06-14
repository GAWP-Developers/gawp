package com.gawpdevelopers.gawp.controllers;

import com.gawpdevelopers.gawp.domain.Applicant;

import com.gawpdevelopers.gawp.services.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gawpdevelopers.gawp.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

@Controller
public class LoginHandler {
    private ApplicantService applicantService;

    @Autowired
    public void setApplicantService(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }


  @RequestMapping({"/"})
  public String index(){
      return "index";
  }
  @RequestMapping({"/login/oauth2"})
  public String login(){
      return "login";
  }

  //@RequestMapping({"/login"})
  //public String login2(){
    //  return "securedPage";
 // }
  @RequestMapping({"/save"})
  public String login(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                      @AuthenticationPrincipal OAuth2User oauth2User, Model model) {


      Map<String, Object> attributes = oauth2User.getAttributes();
      if(applicantService.getByApiId(oauth2User.getName())==null){
          Applicant newApplicant = new Applicant();
          newApplicant.setProvider(authorizedClient.getClientRegistration().getClientName());
          newApplicant.setApiID(oauth2User.getName());
          newApplicant.setEmail((String) attributes.get("email"));
          newApplicant.setUserName((String) attributes.get("name"));
          newApplicant.setPictureUrl((String) attributes.get("picture"));
          newApplicant.setRoles("ROLE_USER");
          newApplicant.setActive(true);
          applicantService.saveOrUpdate(newApplicant);
      }
      model.addAttribute("name", attributes.get("name"));
      return "redirect:applicant/";
}
    @RequestMapping({"/login"})
    public String whoisit(Model  model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("mail",auth.getAuthorities().toString());


        model.addAttribute("name",auth.getName() );

        return "whoisit";
    }
  
  @RequestMapping("/login-error")  
  public String loginError(Model model) {  
      model.addAttribute("msg", " something went wrong. try again.");
      return "index";  
  }

  @RequestMapping("/index")
  public String perIndex(){
      return "/persLogin";
  }

    @RequestMapping(value="/persLogin",method=RequestMethod.GET)
    public String perIndex(Model model){
        model.addAttribute("userForm", new User());
        return "persLogin";
    }
    @RequestMapping(value="/persLogin",method=RequestMethod.POST)
    public String perIndex(@ModelAttribute("userForm") User userForm){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return "check";
    }
    @RequestMapping("/check")
    public String check(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().toString().equals("[ROLE_DEPT]")){
            return "redirect:login";
        }
        
        if(auth.getAuthorities().toString().equals("[ROLE_GRAD]")){
            return "redirect:/grad";
        }

        return "redirect:/logout";
    }
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

}
