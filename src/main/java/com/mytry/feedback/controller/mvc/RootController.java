package com.mytry.feedback.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping(path = "/")
    public String redirectFromRootPage() {
        return "redirect:/feedbacks";
    }
}
