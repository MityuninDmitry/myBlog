package myBlog.repository;

import myBlog.TestUtils;
import myBlog.model.Commentary;
import myBlog.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class H2CommentaryRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CommentaryRepository commentaryRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        TestUtils.deleteAllPostsFromDB(jdbcTemplate);
        TestUtils.deleteAllCommentaryFromDB(jdbcTemplate);
        TestUtils.insertPostsToDBWithComments(jdbcTemplate, 10);

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
