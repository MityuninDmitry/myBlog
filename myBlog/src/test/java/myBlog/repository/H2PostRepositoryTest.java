package myBlog.repository;

import myBlog.TestUtils;
import myBlog.model.Post;
import myBlog.model.PostRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class H2PostRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        TestUtils.deleteAllPostsFromDB(jdbcTemplate);
        TestUtils.deleteAllTagsFromDB(jdbcTemplate);
        TestUtils.insertPostsToDBWithTags(jdbcTemplate,150);

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
