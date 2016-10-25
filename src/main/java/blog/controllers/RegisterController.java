package blog.controllers;

import blog.forms.RegistrationForm;
import blog.services.NotificationService;
import blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notifyService;

    @RequestMapping("/users/register")
    public String login(RegistrationForm loginForm) {
        return "users/register";
    }

    @RequestMapping(value = "/users/register", method = RequestMethod.POST)
    public String registrationPage(@Valid RegistrationForm registrationForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            notifyService.addErrorMessage("Please fill the form correctly!");
            return "users/register";
        }

        if (!userService.authenticate(
                registrationForm.getUsername(), registrationForm.getPassword())) {
            notifyService.addErrorMessage("Invalid login!");
            return "users/register";
        }

        notifyService.addInfoMessage("Registration successful");
        return "redirect:/";
    }
}
