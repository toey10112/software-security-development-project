package JAITUNGMA.product.controller;

import JAITUNGMA.product.annotation.RequiresCaptcha;
import JAITUNGMA.product.dto.SignupDTO;
import JAITUNGMA.product.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class SignupController {

    @Autowired
    private SignupService signupService;

    @GetMapping("/signup")
    public String getSignupPage(SignupDTO user) {
        return "signup"; // return signup.html
    }

    @PostMapping("/signup")
    @RequiresCaptcha
    public String signupUser(@Valid SignupDTO user,
                             BindingResult result,
                             Model model,
                             @ModelAttribute("confirmPassword") String passwordValidator) {

        if (result.hasErrors())
            return "signup";

        String signupError = null;

        if (!signupService.isUsernameAvailable(user.getUsername())) {
            signupError = "The username already exists.";
        }

        if (signupError == null) {
            boolean rowsAdded = signupService.createUser(user, passwordValidator);
            if (!rowsAdded) {
                signupError = "Password confirmation fail";
            }
        }

        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
        }

        else {
            model.addAttribute("signupError", signupError);
        }
        model.addAttribute("signupDto", new SignupDTO());
        return "signup";
    }
}
