package myBlog.repository;

import myBlog.configuration.DataSourceConfiguration;
import myBlog.model.Commentary;
import myBlog.model.Post;
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

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, H2CommentaryRepository.class, H2PostRepository.class})
@TestPropertySource(locations = "classpath:test-application.properties")
public class H2CommentaryRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CommentaryRepository commentaryRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM Post");
        jdbcTemplate.execute("DELETE FROM Commentary");
        for (int i = 0; i < 10; i++) {

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

            String query = "insert into commentary(post_id, text, createDateTime) values(?, ?, ?)";
            jdbcTemplate.update(query,
                    keyHolder.getKey().longValue(), "SomeCommentaryText " + i, LocalDateTime.now());
        }
    }

    @Test
    void getByPostId() {
        Post somePost = postRepository.findAll().getLast();
        List<Commentary> commentaries = commentaryRepository.getByPostId(somePost.getId());
        assertEquals(1,commentaries.size());
    }

    @Test
    void addCommentToPost_shouldAddNewComment() {
        Post somePost = postRepository.findAll().getLast();
        commentaryRepository.addCommentToPost(somePost.getId(), "newCommentary");

        List<Commentary> commentaries = commentaryRepository.getByPostId(somePost.getId());
        assertEquals(2,commentaries.size());
        assertEquals("newCommentary", commentaries.getLast().getText());
    }

    @Test
    void updateComment_shouldUpdateText() {
        Post somePost = postRepository.findAll().getLast();
        Commentary commentary = commentaryRepository.getByPostId(somePost.getId()).getLast();

        String beforeUpdateCommentaryText = commentaryRepository.getByPostId(somePost.getId()).getLast().getText();
        System.out.println(beforeUpdateCommentaryText);
        commentaryRepository.updateComment(commentary.getId(), "updatedCommentary");

        List<Commentary> commentaries = commentaryRepository.getByPostId(somePost.getId());
        assertEquals(1,commentaries.size());
        assertEquals("updatedCommentary", commentaries.getLast().getText());
        assertNotEquals(beforeUpdateCommentaryText, commentaries.getLast().getText());
    }

    @Test
    void deleteById_shouldDeleteById() {
        Long post_id = postRepository.findAll().getLast().getId();
        Long id = commentaryRepository.getByPostId(post_id).getLast().getId();
        commentaryRepository.deleteById(id);

        assertEquals(0, commentaryRepository.getByPostId(post_id).size());
    }
}
