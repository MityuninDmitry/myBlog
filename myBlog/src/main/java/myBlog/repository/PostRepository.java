package myBlog.repository;

import myBlog.model.Post;
import myBlog.model.PostRequest;

import java.util.List;

public interface PostRepository {
    List<Post> findAll();
    List<Post> findAllPaginated(int size, int page);
    Post getById(Long id);
    void incrementLikeCounterById(Long id);
    Long create(PostRequest postRequest);
    void update(Long id, PostRequest postRequest);
    void deleteById(Long id);
}
