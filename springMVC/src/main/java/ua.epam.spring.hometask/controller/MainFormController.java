package ua.epam.spring.hometask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainFormController {
    @RequestMapping("/")
    public String mainFrom() {
        return "mainForm";
    }
}