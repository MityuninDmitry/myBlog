package myBlog.repository;

import myBlog.TestUtils;
import myBlog.model.Post;
import myBlog.model.Tag;
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

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class H2TagRepositoryTest {

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
        TestUtils.insertPostsToDBWithDoubleTags(jdbcTemplate, 150);

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
