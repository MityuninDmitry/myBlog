package myBlog.repository;

import model.Commentary;
import model.Tag;
import myBlog.repository.mapper.CommentaryRowMapper;
import myBlog.repository.mapper.TagRowMapper;
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
}
