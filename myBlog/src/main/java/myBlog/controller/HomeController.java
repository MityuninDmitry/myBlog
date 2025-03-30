package myBlog.controller;

import model.Post;
import model.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getAllPosts(Model model) {

        List<Post> posts = List.of(
                new Post(1L, "Name 1", "Some description text", List.of(new Tag(1L, "Tag1"), new Tag(2L, "Tag2")), 0, new ArrayList<>(),null,"https://img.freepik.com/premium-vector/default-image-icon-vector-missing-picture-page-website-design-mobile-app-no-photo-available_87543-11093.jpg?w=1800", LocalDateTime.now()),
                new Post(2L, "Name 2", "Some description text", List.of(new Tag(1L, "Tag1"), new Tag(2L, "Tag2")), 0, new ArrayList<>(),null,"https://img.freepik.com/premium-vector/default-image-icon-vector-missing-picture-page-website-design-mobile-app-no-photo-available_87543-11093.jpg?w=1800", LocalDateTime.now())
        );

        model.addAttribute("posts", posts);
        return "Posts";
    }
}
