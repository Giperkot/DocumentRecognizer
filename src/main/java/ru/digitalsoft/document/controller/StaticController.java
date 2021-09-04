package ru.digitalsoft.document.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class StaticController {

    @GetMapping({"/DocumentRecognizer/"})
    public String index () {
        return "/DocumentRecognizer/index.html";
    }

    @GetMapping({"/login/", "/login"})
    public String login () {
        return "/login.html";
    }

    @GetMapping({"/", "/DocumentRecognizer"})
    public RedirectView redirectWithUsingRedirectView(
            RedirectAttributes attributes) {
        return new RedirectView("/DocumentRecognizer/");
    }

}
