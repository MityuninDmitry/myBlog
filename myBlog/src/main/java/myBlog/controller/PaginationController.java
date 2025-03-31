package myBlog.controller;

import myBlog.service.PaginationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pagination")
public class PaginationController {
    private final PaginationService paginationService;

    public PaginationController(PaginationService paginationService) {
        this.paginationService = paginationService;
    }

    @GetMapping
    public String handlePagination(@RequestParam("size") int size, @RequestParam("page") int page, Model model) {
        // Добавьте данные в модель, чтобы передать их в представление (если нужно)
        paginationService.updatePagination(size, page);
        return "redirect:/";
    }
}
