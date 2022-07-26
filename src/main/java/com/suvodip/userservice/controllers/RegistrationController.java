package com.suvodip.userservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.suvodip.userservice.exceptions.InvalidTokenException;
import com.suvodip.userservice.service.UserService;

@Controller
public class RegistrationController {

	@Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;
    
    private String REDIRECT_LOGIN = "redirect:/api/login";

    @GetMapping("/user/verify")
    public String verifyUser(@RequestParam(required = false) String token, final Model model, RedirectAttributes redirAttr){
    	System.out.println("token-------------"+token);
        if(token==null || token==""){
            redirAttr.addFlashAttribute("tokenError", messageSource.getMessage("user.registration.verification.missing.token", null,LocaleContextHolder.getLocale()));
            return REDIRECT_LOGIN;
        }

        try {
              userService.verifyUser(token);
        } catch (InvalidTokenException e) {
            redirAttr.addFlashAttribute("tokenError", messageSource.getMessage("user.registration.verification.invalid.token", null,LocaleContextHolder.getLocale()));
            return REDIRECT_LOGIN;
        }

        redirAttr.addFlashAttribute("verifiedAccountMsg", messageSource.getMessage("user.registration.verification.success", null,LocaleContextHolder.getLocale()));
        return REDIRECT_LOGIN;
    }
}
