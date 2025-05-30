package myBlog.controller;

import myBlog.model.Post;
import myBlog.model.PostRecord;
import myBlog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
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
    public String createNewPost(@ModelAttribute PostRecord postRecord) {
        service.createNewPost(postRecord);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updatePost(@PathVariable("id") Long id, @ModelAttribute PostRecord postRecord) {
        service.updatePost(id, postRecord);
        service.deleteTagsByPostId(id);
        service.createTagsInPost(id, postRecord.tags());
        return "redirect:/posts/" + id;
    }

    @GetMapping("/addManyPosts")
    public String addManyPosts() {
        for (int i = 1; i <= 150; i++) {
            PostRecord postRecord = new PostRecord("Number " + i, "Description " + i, "https://cs13.pikabu.ru/post_img/big/2024/05/16/9/1715872409131237032.jpg", "Tag1, Tag2, Tag" + i);
            service.createNewPost(postRecord);
        }
        return "redirect:/";
    }
}