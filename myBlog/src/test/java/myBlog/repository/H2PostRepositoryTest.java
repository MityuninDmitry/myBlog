package myBlog.repository;

import myBlog.configuration.DataSourceConfiguration;
import myBlog.model.Post;
import myBlog.model.PostRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, H2PostRepository.class, H2TagRepository.class})
@TestPropertySource(locations = "classpath:test-application.properties")
public class H2PostRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM Post");
        jdbcTemplate.execute("DELETE FROM Tag");
        for (int i = 0; i < 150; i++) {
            String postQuery = "INSERT INTO Post (name, description, likeCounter, imageURL, createDateTime) values(?, ?, ?, ?, ?)";
            // Создаём KeyHolder для хранения сгенерированного ключа
            KeyHolder keyHolder = new GeneratedKeyHolder();
            final long id = i;
            // Выполняем запрос с использованием PreparedStatement
            jdbcTemplate.update(connection -> {
                var ps = connection.prepareStatement(postQuery, new String[]{"id"}); // Указываем, что хотим вернуть столбец "id"
                ps.setString(1, "Post " + id);
                ps.setString(2, "someDesc");
                ps.setInt(3, 0);
                ps.setString(4, "https://someImage.jpg");
                ps.setObject(5, LocalDateTime.now());
                return ps;
            }, keyHolder);

            String query = "insert into tag(post_id, name) values(?, ?)";
            jdbcTemplate.update(query, keyHolder.getKey().longValue(), "Post"+i);
        }
    }

    @Test
    void findAllPaginated_shouldReturnExactPostCount() {
        List<Post> posts = postRepository.findAllPaginated(10,1);
        assertEquals(10,posts.size());
        assertEquals("Post 10", posts.getFirst().getName());
        assertEquals("Post 19", posts.getLast().getName());
    }

    @Test
    void findAllPaginatedByTag_shouldReturnExactPostCount() {
        List<Post> posts = postRepository.findAllPaginatedByTag(10,0,"Post3");
        assertEquals(1,posts.size());
        assertEquals("Post 3", posts.getFirst().getName());
    }

    @Test
    void update_shouldUpdatePost() {
        Post beforeUpdatePost = postRepository.findAll().getLast();
        PostRecord postRecord = new PostRecord( "updatedName", "updatedDes", "updatedURL", "");
        postRepository.update(beforeUpdatePost.getId(), postRecord);
        Post afterUpdatePost = postRepository.getById(beforeUpdatePost.getId());

        assertNotEquals(beforeUpdatePost.getDescription(), afterUpdatePost.getDescription());
        assertNotEquals(beforeUpdatePost.getName(), afterUpdatePost.getName());
        assertNotEquals(beforeUpdatePost.getImageURL(), afterUpdatePost.getImageURL());
    }

    @Test
    void deleteById_shouldDeleteById() {
        Long id = postRepository.findAll().getLast().getId();
        postRepository.deleteById(id);

        assertThrowsExactly(EmptyResultDataAccessException.class, () -> postRepository.getById(id));
    }

}
