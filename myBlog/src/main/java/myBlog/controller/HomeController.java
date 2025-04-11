package myBlog.controller;

import myBlog.model.Post;
import myBlog.service.PaginationService;
import myBlog.service.PostService;
import myBlog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    private final PostService postService;

    private final PaginationService paginationService;

    private final TagService tagService;

    @Autowired
    public HomeController(PostService postService, PaginationService paginationService, TagService tagService) {
        this.postService = postService;
        this.paginationService = paginationService;
        this.tagService = tagService;
    }


    @GetMapping("/")
    public String getAllPosts(Model model) {
        List<Post> posts = postService.findAllFiltered();

        model.addAttribute("posts", posts);
        model.addAttribute("pagination", paginationService.getPagination());
        model.addAttribute("tags",tagService.distinctTags());
        model.addAttribute("selectedTag",tagService.getSelectedTag());
        return "Posts";
    }


}
