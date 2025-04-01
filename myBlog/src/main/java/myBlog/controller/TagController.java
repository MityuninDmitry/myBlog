package myBlog.controller;

import myBlog.model.Post;
import myBlog.service.PaginationService;
import myBlog.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;
    private final PaginationService paginationService;

    public TagController(TagService tagService, PaginationService paginationService) {
        this.tagService = tagService;
        this.paginationService = paginationService;
    }

    @GetMapping("/{name}")
    public String setSelectedTag(@PathVariable("name") String name) {
        tagService.setSelectedTag(name);
        paginationService.updatePagination(paginationService.getPagination().getSize(),0);
        return "redirect:/";
    }

    @GetMapping("/")
    public String setSelectedTagEmpty() {
        tagService.setSelectedTag("");
        paginationService.updatePagination(paginationService.getPagination().getSize(),0);
        return "redirect:/";
    }
}
