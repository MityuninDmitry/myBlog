package myBlog.controller;

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
        jdbcTemplate.execute("delete from Post");
        jdbcTemplate.execute("INSERT INTO Post (id, name, description, likeCounter, imageURL, createDateTime) VALUES (1,'Post 1', 'Description', 10, 'https://cdn.dlcompare.com/others_jpg/upload/news/image/v-cyberpunk-2077-mojno-igrat-kak-v-gta-no-ne-bez-posledstviy-image-116915b0a.jpeg.webp', CURRENT_TIMESTAMP);");
    }

    @Test
    void incrementLikeCounterById_shouldIncrementCounterAndRedirectToPost() throws Exception {
        mockMvc.perform(get("/posts/like/1"))
                .andExpect(redirectedUrl("/posts/1"));
        assertEquals(11,postService.getById(1L).getLikeCounter());
    }

    @Test
    void incrementLikeCounterById_fewTimes() throws Exception {
        mockMvc.perform(get("/posts/like/1"));
        mockMvc.perform(get("/posts/like/1"));

        assertEquals(12,postService.getById(1L).getLikeCounter());
    }


}
