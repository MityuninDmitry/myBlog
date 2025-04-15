package myBlog.controller;

import myBlog.model.Post;
import myBlog.service.PostService;
import myBlog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final PostService postService;

    private final TagService tagService;

    @Autowired
    public HomeController(PostService postService, TagService tagService) {
        this.postService = postService;
        this.tagService = tagService;
    }

    @GetMapping("/")
    public String getAllPosts(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String selectedTag) {

        List<Post> posts = postService.findAllFiltered(page, size, selectedTag);

        model.addAttribute("posts", posts);
        model.addAttribute("tags",tagService.distinctTags());

        model.addAttribute("paginationSize", size);
        model.addAttribute("paginationPage", page);
        model.addAttribute("selectedTag", selectedTag);
        return "Posts";
    }


}
