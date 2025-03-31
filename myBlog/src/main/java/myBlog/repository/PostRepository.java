package myBlog.repository;

import myBlog.model.Post;
import myBlog.model.PostRequest;

import java.util.List;

public interface PostRepository {
    List<Post> findAll();
    Post getById(Long id);
    void incrementLikeCounterById(Long id);
    Long create(PostRequest postRequest);
    void update(Long id, PostRequest postRequest);
    void deleteById(Long id);
}
