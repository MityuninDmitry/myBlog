package myBlog.controller;

import myBlog.model.Post;
import myBlog.service.PaginationService;
import myBlog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final PostService postService;
    private final PaginationService paginationService;

    public HomeController(PostService postService, PaginationService paginationService) {
        this.postService = postService;
        this.paginationService = paginationService;
    }


    @GetMapping("/")
    public String getAllPosts(Model model) {

        List<Post> posts = postService.findAllPaginated();

        model.addAttribute("posts", posts);
        model.addAttribute("pagination", paginationService.getPagination());
        return "Posts";
    }


}
