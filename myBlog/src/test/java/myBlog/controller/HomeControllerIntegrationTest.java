package myBlog.controller;

import myBlog.TestUtils;
import myBlog.service.PostService;
import myBlog.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class HomeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PostService postService;

    @BeforeEach
    void setUp() {
        TestUtils.deleteAllPostsFromDB(jdbcTemplate);
        TestUtils.insertPostsToDB(jdbcTemplate, 150);
    }

    @Test
    void getAllPosts_defaultPaginatedWithoutFilters() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(view().name("Posts"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attributeExists("paginationPage"))
                .andExpect(model().attributeExists("paginationSize"))
                .andExpect(model().attributeExists("tags"));

        assertEquals(10, postService.findAllFiltered(0,10,null).stream().count());
    }

    @Test
    void getAllPosts_defaultPaginatedFilteredByTag() throws Exception {
        Long existedPostId = postService.findAll().stream().findFirst().get().getId();
        String uniqTag = "uniqTag";
        postService.createTagsInPost(existedPostId,uniqTag);

        mockMvc.perform(get("/")
                        .param("selectedTag","uniqTag"))
                .andExpect(view().name("Posts"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attributeExists("paginationSize"))
                .andExpect(model().attributeExists("paginationPage"))
                .andExpect(model().attributeExists("tags"))
                .andExpect(model().attributeExists("selectedTag"));


        assertEquals(1, postService.findAllFiltered(0,10,"uniqTag").stream().count());
    }

    public static Stream<Integer> provideSize() {
        return Stream.of(10,20,50);
    }

    @ParameterizedTest
    @MethodSource("provideSize")
    void handlePagination_shouldRedirectAndUpdatePagination(Integer size) throws Exception {
        mockMvc.perform(get("/")
                        .param("size",String.valueOf(size))
                        .param("page","0"))
                .andExpect(view().name("Posts"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attributeExists("paginationSize"))
                .andExpect(model().attributeExists("paginationPage"))
                .andExpect(model().attributeExists("tags"));
    }
}
