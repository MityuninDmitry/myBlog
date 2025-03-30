package myBlog.controller;

import myBlog.service.CommentaryService;
import myBlog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/posts/commentary")
public class CommentaryController {
    private final CommentaryService service;

    public CommentaryController(CommentaryService service) {
        this.service = service;
    }
    @PostMapping("/{id}")
    public String addComment(@PathVariable("id") Long post_id, @RequestParam("text") String text) {
        // Логика сохранения комментария в базе данных
        service.addCommentToPost(post_id, text);
        // Перенаправление обратно на страницу поста
        return "redirect:/posts/" + post_id;
    }

    @PostMapping("/update/{id}")
    public String updateComment(@PathVariable("id") Long id, @RequestParam("text") String text, @RequestParam("post_id") Long post_id) {
        // Логика сохранения комментария в базе данных
        service.updateComment(id, text);
        // Перенаправление обратно на страницу поста
        return "redirect:/posts/" + post_id;
    }
}
