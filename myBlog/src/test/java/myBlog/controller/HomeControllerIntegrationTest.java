package myBlog.controller;

import myBlog.service.PaginationService;
import myBlog.service.PostService;
import myBlog.service.TagService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
    @Autowired
    private PaginationService paginationService;
    @Autowired
    private TagService tagService;


    @BeforeEach
    void setUp() {

        jdbcTemplate.execute("delete from Post");
        for (int i = 0; i < 150; i++) {
            jdbcTemplate.execute("INSERT INTO Post (name, description, likeCounter, imageURL, createDateTime) VALUES ('Post 1', 'Description', 10, 'https://cdn.dlcompare.com/others_jpg/upload/news/image/v-cyberpunk-2077-mojno-igrat-kak-v-gta-no-ne-bez-posledstviy-image-116915b0a.jpeg.webp', CURRENT_TIMESTAMP);");
        }
    }
    @Test
    void getAllPosts_defaultPaginatedWithoutFilters() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(view().name("Posts"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attributeExists("pagination"))
                .andExpect(model().attributeExists("tags"))
                .andExpect(model().attributeExists("selectedTag"));

        assertEquals(10,paginationService.getPagination().getSize());
        assertEquals(0,paginationService.getPagination().getPage());
        assertEquals(10, postService.findAllFiltered().stream().count());
        assertEquals("", tagService.getSelectedTag().getName());
    }

    @Test
    void getAllPosts_defaultPaginatedFilteredByTag() throws Exception {
        Long existedPostId = postService.findAll().stream().findFirst().get().getId();
        String uniqTag = "uniqTag";
        postService.createTagsInPost(existedPostId,uniqTag);
        tagService.setSelectedTag(uniqTag);

        mockMvc.perform(get("/"))
                .andExpect(view().name("Posts"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attributeExists("pagination"))
                .andExpect(model().attributeExists("tags"))
                .andExpect(model().attributeExists("selectedTag"));


        assertEquals(10,paginationService.getPagination().getSize());
        assertEquals(0,paginationService.getPagination().getPage());
        assertEquals(1, postService.findAllFiltered().stream().count());
        assertEquals(uniqTag, tagService.getSelectedTag().getName());
    }

}
