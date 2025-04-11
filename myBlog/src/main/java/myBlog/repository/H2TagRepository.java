package myBlog.repository;

import myBlog.model.Tag;
import myBlog.repository.mapper.TagRowMapper;
import myBlog.repository.mapper.TagWithoutIdRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class H2TagRepository implements TagRepository{
    private final JdbcTemplate jdbcTemplate;

    public H2TagRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> getByPostId(long post_id) {
        String query = "select id, name from Tag where post_id = ?";
        List<Tag> tags = jdbcTemplate.query(query, new Object[]{post_id}, new TagRowMapper());
        return tags;
    }

    @Override
    public void createLinkedTag(Long post_id, String name) {
        String query = "insert into tag(post_id, name) values(?, ?)";
        jdbcTemplate.update(query,
                post_id, name);
    }

    @Override
    public void deleteTagsByPostId(Long post_id) {
        String query = "delete from tag where post_id = ?";
        jdbcTemplate.update(query,post_id);
    }

    @Override
    public List<Tag> distinctTags() {
        String query = "select distinct name from Tag";
        List<Tag> tags = jdbcTemplate.query(query, new TagWithoutIdRowMapper());
        return tags;
    }
}
