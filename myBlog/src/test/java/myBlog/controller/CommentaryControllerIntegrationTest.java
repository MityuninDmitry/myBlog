package myBlog.controller;

import myBlog.TestUtils;
import myBlog.model.Commentary;
import myBlog.model.Post;
import myBlog.service.CommentaryService;
import myBlog.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class CommentaryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CommentaryService commentaryService;
    @Autowired
    private PostService postService;

    @BeforeEach
    void setUp() {
        TestUtils.deleteAllPostsFromDB(jdbcTemplate);
        TestUtils.deleteAllCommentaryFromDB(jdbcTemplate);
        TestUtils.insertPostsToDBWithComments(jdbcTemplate,10);
    }

    @Test
    public void addComment_shouldCreateNewPost() throws Exception {
        Long post_id = postService.findAll().getLast().getId();
        String commentaryText = "newCommentaryText";
        mockMvc.perform(post("/posts/commentary/" + post_id)
                .param("text",commentaryText))
                .andExpect(redirectedUrl("/posts/" + post_id));

        List<Commentary> commentaries = commentaryService.getByPostId(post_id);
        assertEquals(2, commentaries.size());
        assertEquals(commentaryText,commentaries.getLast().getText());
    }

    @Test
    public void updateComment_shouldUpdateTextComment() throws Exception {
        Post somePost = postService.findAll().getLast();
        Long comment_id = somePost.getCommentaries().getLast().getId();
        String updatedCommentaryText = "updatedCommentaryText";

        mockMvc.perform(post("/posts/commentary/update/" + comment_id)
                .param("text", updatedCommentaryText)
                .param("post_id", String.valueOf(somePost.getId())))
                .andExpect(redirectedUrl("/posts/" + somePost.getId()));

        assertEquals(updatedCommentaryText, commentaryService.getById(comment_id).getLast().getText());
    }

    @Test
    public void deleteById_shouldDeleteComment() throws Exception {
        Post somePost = postService.findAll().getLast();
        Long comment_id = somePost.getCommentaries().getLast().getId();

        mockMvc.perform(get("/posts/commentary/delete/" + comment_id)
                        .param("post_id", String.valueOf(somePost.getId())))
                .andExpect(redirectedUrl("/posts/" + somePost.getId()));

        assertEquals(0, commentaryService.getByPostId(somePost.getId()).size());
    }
}
