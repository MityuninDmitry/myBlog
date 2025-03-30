package myBlog.controller;

import model.Post;
import myBlog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts/like")
public class LikeController {
    private final PostService service;

    public LikeController(PostService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public String incrementLikeCounterById(@PathVariable("id") Long id) {
        service.incrementLikeCounterById(id);
        return "redirect:/posts/" + id;
    }
}
