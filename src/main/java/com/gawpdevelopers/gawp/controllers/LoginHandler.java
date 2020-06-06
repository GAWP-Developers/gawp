package com.gawpdevelopers.gawp.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gawpdevelopers.gawp.domain.User;

@Controller
public class LoginHandler {

  @RequestMapping({"/"})
  public String index(){
      return "index";
  }
  @RequestMapping({"/login/oauth2"})
  public String login(){
      return "login";
  }
  @RequestMapping({"/login"})
  public String login2(){
      return "securedPage";
  }
  @RequestMapping({"/securedPage"})
  public String login(Model model,  
          @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,  
          @AuthenticationPrincipal OAuth2User oauth2User) {  
		model.addAttribute("userName", oauth2User.getName());  
		model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());  
		model.addAttribute("userAttributes", oauth2User.getAttributes());  
		return "securedPage";  
}
  
  @RequestMapping("/login-error")  
  public String loginError(Model model) {  
      model.addAttribute("loginError", true);  
      return "index";  
  }
  @RequestMapping(value="/persLogin",method=RequestMethod.GET)
  public String perIndex(Model model){
	  model.addAttribute("userForm", new User());
      return "persLogin";
  }
  @RequestMapping(value="/persLogin",method=RequestMethod.POST)
  public String perIndex(@ModelAttribute("userForm") User userForm){
	  System.out.println("name "+userForm.getUserName());
      return "advert";
  }
}
