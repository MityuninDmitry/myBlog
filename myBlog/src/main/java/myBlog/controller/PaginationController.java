package myBlog.controller;

import myBlog.service.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pagination")
public class PaginationController {

    @Autowired
    private final PaginationService paginationService;

    public PaginationController(PaginationService paginationService) {
        this.paginationService = paginationService;
    }

    @GetMapping
    public String handlePagination(@RequestParam("size") int size, @RequestParam("page") int page) {
        paginationService.updatePagination(size, page);
        return "redirect:/";
    }
}
