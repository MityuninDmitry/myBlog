package myBlog.controller;


import myBlog.WebConfiguration;
import myBlog.configuration.DataSourceConfiguration;
import myBlog.service.PaginationService;
import myBlog.service.PostService;
import myBlog.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, WebConfiguration.class})
@WebAppConfiguration
@TestPropertySource(locations = "classpath:test-application.properties")
public class HomeControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PostService postService;
    @Autowired
    private PaginationService paginationService;
    @Autowired
    private TagService tagService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

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

    //@GetMapping("/")
    //    public String getAllPosts(Model model) {
    //        List<Post> posts = postService.findAllFiltered();
    //
    //        model.addAttribute("posts", posts);
    //        model.addAttribute("pagination", paginationService.getPagination());
    //        model.addAttribute("tags",tagService.distinctTags());
    //        model.addAttribute("selectedTag",tagService.getSelectedTag());
    //        return "Posts";
    //    }

}
