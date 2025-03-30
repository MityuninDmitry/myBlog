package myBlog.controller;

import model.Commentary;
import model.Post;
import model.Tag;
import myBlog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }
    @GetMapping("/{id}")
    public String getPostById(@PathVariable("id") Long id, Model model) {

        Post post = service.getById(id);

        model.addAttribute("post", post);
        return "PostDetails";
    }



}