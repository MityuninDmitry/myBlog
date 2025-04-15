package myBlog.controller;

import myBlog.TestUtils;
import myBlog.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class LikeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PostService postService;

    @BeforeEach
    void setUp() {
        TestUtils.deleteAllPostsFromDB(jdbcTemplate);
        TestUtils.insertPostsToDB(jdbcTemplate, 1);
    }

    @Test
    void incrementLikeCounterById_shouldIncrementCounterAndRedirectToPost() throws Exception {
        long somePostId = postService.findAll().getFirst().getId();
        mockMvc.perform(get("/posts/like/" + somePostId))
                .andExpect(redirectedUrl("/posts/" + somePostId));
        assertEquals(11,postService.getById(somePostId).getLikeCounter());
    }

    @Test
    void incrementLikeCounterById_fewTimes() throws Exception {
        long somePostId = postService.findAll().getFirst().getId();
        mockMvc.perform(get("/posts/like/" + somePostId));
        mockMvc.perform(get("/posts/like/" + somePostId));

        assertEquals(12,postService.getById(somePostId).getLikeCounter());
    }


}
