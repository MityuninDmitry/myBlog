package myBlog.repository;

import myBlog.model.Commentary;
import myBlog.repository.mapper.CommentaryRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public class H2CommentaryRepository implements CommentaryRepository {

    private final JdbcTemplate jdbcTemplate;

    public H2CommentaryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Commentary> getByPostId(Long post_id) {
        String query = "select id, text, createDateTime from Commentary where post_id = ?";
        List<Commentary> commentaries = jdbcTemplate.query(query, new Object[]{post_id}, new CommentaryRowMapper());
        return commentaries;
    }

    @Override
    public List<Commentary> getById(Long id) {
        String query = "select id, text, createDateTime from Commentary where id = ?";
        List<Commentary> commentaries = jdbcTemplate.query(query, new Object[]{id}, new CommentaryRowMapper());
        return commentaries;
    }

    @Override
    public void addCommentToPost(Long post_id, String text) {
        LocalDateTime now = LocalDateTime.now();
        String query = "insert into commentary(post_id, text, createDateTime) values(?, ?, ?)";
        jdbcTemplate.update(query,
                post_id, text, now);
    }

    @Override
    public void updateComment(Long id, String text) {
        String query = "update commentary set text = ? where id = ?";
        jdbcTemplate.update(query,text,id);
    }

    @Override
    public void deleteById(Long id) {
        String query = "delete commentary where id = ?";
        jdbcTemplate.update(query,id);
    }
}
