package myBlog.repository;

import model.Post;
import myBlog.repository.mapper.PostRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class H2PostRepository  implements PostRepository{

    private final JdbcTemplate jdbcTemplate;

    public H2PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Post> findAll() {
        String query = "select id, name, description, likeCounter, imageURL, createDateTime from Post";
        List<Post> posts = jdbcTemplate.query(query, new PostRowMapper());
        return posts;
    }

    @Override
    public Post getById(Long id) {
        String query = "select id, name, description, likeCounter, imageURL, createDateTime from Post where id = ?";
        Post post = jdbcTemplate.queryForObject(query, new Object[]{id}, new PostRowMapper());
        return post;
    }

    @Override
    public void incrementLikeCounterById(Long id) {
        String query = "update Post SET likeCounter = likeCounter + 1 where id = ?";
        jdbcTemplate.update(query,id);
    }


}


