package myBlog.repository;

import myBlog.model.Post;

import java.util.List;

public interface PostRepository {
    List<Post> findAll();
    Post getById(Long id);
    void incrementLikeCounterById(Long id);
}
