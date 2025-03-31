package myBlog.repository;

import myBlog.model.Commentary;
import myBlog.model.Post;
import myBlog.model.PostRequest;
import myBlog.model.Tag;
import myBlog.repository.mapper.PostRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class H2PostRepository  implements PostRepository{

    private final JdbcTemplate jdbcTemplate;

    public H2PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Post> findAll() {
        String query = "select id, name, description, likeCounter, imageURL, createDateTime from Post order by createdatetime asc";
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

    @Override
    public Long create(PostRequest postRequest) {
        LocalDateTime now = LocalDateTime.now();
        String query = "insert into post(name, description, likeCounter, imageURL, createDateTime) values(?, ?, ?, ?, ?)";

        // Создаём KeyHolder для хранения сгенерированного ключа
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // Выполняем запрос с использованием PreparedStatement
        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(query, new String[]{"id"}); // Указываем, что хотим вернуть столбец "id"
            ps.setString(1, postRequest.name());
            ps.setString(2, postRequest.description());
            ps.setInt(3, 0); // likeCounter
            ps.setString(4, postRequest.imageURL());
            ps.setObject(5, now);
            return ps;
        }, keyHolder);

        // Возвращаем сгенерированный ID
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Long id, PostRequest postRequest) {
        String query = "update Post SET name = ?, description = ?, imageURL = ? where id = ?";
        jdbcTemplate.update(query,postRequest.name(), postRequest.description(), postRequest.imageURL(), id);
    }

    @Override
    public void deleteById(Long id) {
        String query = "delete from post where id = ?";
        jdbcTemplate.update(query, id);
    }


}


