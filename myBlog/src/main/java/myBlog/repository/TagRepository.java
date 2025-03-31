package myBlog.repository;

import myBlog.model.Tag;

import java.util.List;

public interface TagRepository {
    List<Tag> getByPostId(long post_id);
    void createLinkedTag(Long post_id, String name);
    void deleteTagsByPostId(Long post_id);
}
