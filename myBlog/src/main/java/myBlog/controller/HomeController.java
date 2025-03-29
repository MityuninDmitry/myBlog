package myBlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @GetMapping("/")
    @ResponseBody
    public String homePage() {
        return "<h1>Hello, you are in my Blog Web App</h1>"; // Ответ
    }
}
