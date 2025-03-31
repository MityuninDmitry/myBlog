package myBlog.controller;

import myBlog.model.Post;
import myBlog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final PostService service;

    public HomeController(PostService service) {
        this.service = service;
    }


    @GetMapping("/")
    public String getAllPosts(Model model) {

        List<Post> posts = service.findAll();

        model.addAttribute("posts", posts);
        return "Posts";
    }
}
