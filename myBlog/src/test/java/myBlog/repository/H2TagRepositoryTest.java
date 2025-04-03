package myBlog.repository;

import myBlog.configuration.DataSourceConfiguration;
import myBlog.model.Post;
import myBlog.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, H2TagRepository.class, H2PostRepository.class})
@TestPropertySource(locations = "classpath:test-application.properties")
public class H2TagRepositoryTest {

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
            jdbcTemplate.update(query, keyHolder.getKey().longValue(), "Tag"+keyHolder.getKey().longValue());
            jdbcTemplate.update(query, keyHolder.getKey().longValue(), "Tag"+keyHolder.getKey().longValue());
        }
    }

    @Test
    void getByPostId_shouldReturnTags() {
        Post post = postRepository.findAll().getFirst();
        List<Tag> tags = tagRepository.getByPostId(post.getId());
        assertEquals(2, tags.size());
    }

    @Test
    void createLinkedTag_shouldCreateNewTag() {
        Post post = postRepository.findAll().getFirst();
        tagRepository.createLinkedTag(post.getId(), "Some Tag");
        assertEquals(3, tagRepository.getByPostId(post.getId()).size());
    }

    @Test
    void deleteTagsByPostId_shouldDeleteAllTags() {
        Post post = postRepository.findAll().getFirst();
        tagRepository.deleteTagsByPostId(post.getId());
        assertEquals(0, tagRepository.getByPostId(post.getId()).size());
    }

    @Test
    void distinctTags_shouldReturnUniqTagNames() {
        List<Tag> tags = tagRepository.distinctTags();
        assertEquals(150, tags.size());
    }
}
