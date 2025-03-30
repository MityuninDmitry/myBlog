package myBlog.repository;

import model.Post;

import java.util.List;

public interface PostRepository {
    List<Post> findAll();
    Post getById(Long id);
}
