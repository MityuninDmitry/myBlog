package myBlog.repository;

import myBlog.model.Commentary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CommentaryRepository  {
    List<Commentary> getByPostId( Long post_id);


    void addCommentToPost( Long post_id,  String text);


    void updateComment(Long id, String text);


    void deleteById(Long id);
}
