package myBlog.repository;

import model.Tag;

import java.util.List;

public interface TagRepository {
    List<Tag> getByPostId(long post_id);
}
