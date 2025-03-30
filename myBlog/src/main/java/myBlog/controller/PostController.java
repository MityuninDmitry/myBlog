package myBlog.controller;

import model.Commentary;
import model.Post;
import model.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    @GetMapping("/{id}")
    public String getPostById(@PathVariable("id") Long id, Model model) {

        Post post = new Post(1L, "Name 1", "Some description text", List.of(new Tag(1L, "Tag1"), new Tag(2L, "Tag2")), 0, List.of(new Commentary(1L, "dasdasd asd asd "),new Commentary(2L, "Second 2")),null,"https://img.freepik.com/premium-vector/default-image-icon-vector-missing-picture-page-website-design-mobile-app-no-photo-available_87543-11093.jpg?w=1800", LocalDateTime.now());

        model.addAttribute("post", post);
        return "PostDetails";
    }
}