package myBlog.repository;

import myBlog.model.Commentary;

import java.util.List;

public interface CommentaryRepository {
    List<Commentary> getByPostId(Long post_id);
    void addCommentToPost(Long post_id, String text);
    void updateComment(Long id, String text);
    void deleteById(Long id);
}
