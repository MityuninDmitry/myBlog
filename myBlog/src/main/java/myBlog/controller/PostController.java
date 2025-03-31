package myBlog.controller;

import myBlog.model.Post;
import myBlog.model.PostRequest;
import myBlog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


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
        String tagsArrayString = post.getTags().stream().map(tag -> tag.getName()).collect(Collectors.joining(", "));
        model.addAttribute("post", post);
        model.addAttribute("tagsArray",tagsArrayString);
        return "PostDetails";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id) {

        service.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/new")
    public String createNewPost(@ModelAttribute PostRequest postRequest) {
        service.createNewPost(postRequest);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updatePost(@PathVariable("id") Long id, @ModelAttribute PostRequest postRequest) {
        service.updatePost(id, postRequest);
        service.deleteTagsByPostId(id);
        service.createTagsInPost(id,postRequest.tags());
        return "redirect:/posts/" + id;
    }
}