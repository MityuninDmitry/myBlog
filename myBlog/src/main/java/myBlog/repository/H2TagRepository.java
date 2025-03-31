package myBlog.repository;

import myBlog.model.Tag;
import myBlog.repository.mapper.TagRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
}
