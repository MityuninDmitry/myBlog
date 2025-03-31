package myBlog.repository;

import myBlog.model.Post;
import myBlog.model.PostRecord;

import java.util.List;

public interface PostRepository {
    List<Post> findAll();
    List<Post> findAllPaginated(int size, int page);
    Post getById(Long id);
    void incrementLikeCounterById(Long id);
    Long create(PostRecord postRecord);
    void update(Long id, PostRecord postRecord);
    void deleteById(Long id);
}
