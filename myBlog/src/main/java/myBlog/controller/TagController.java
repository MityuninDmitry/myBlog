package myBlog.controller;

import myBlog.service.PaginationService;
import myBlog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private final TagService tagService;
    @Autowired
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
