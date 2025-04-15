package myBlog.controller;

import myBlog.TestUtils;
import myBlog.model.Post;
import myBlog.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class PostControllerIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        TestUtils.deleteAllPostsFromDB(jdbcTemplate);
        TestUtils.insertPostsToDB(jdbcTemplate, 150);
    }

    @Test
    void getPostById_checkModelAndReturnedView() throws Exception {
        String someRealPostId = String.valueOf(postService.findAll().getLast().getId());
        mockMvc.perform(get("/posts/" + someRealPostId))
                .andExpect(view().name("PostDetails"))
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attributeExists("tagsArray"));
    }

    @Test
    void deleteById_shouldDeleteItemAndRedirect() throws Exception {
        mockMvc.perform(get("/posts/delete/4"))
                .andExpect(redirectedUrl("/"));
        assertThrowsExactly(EmptyResultDataAccessException.class, () -> postService.getById(4L));
    }

    @Test
    void createNewPost_shouldCreateNewPost() throws Exception {
        mockMvc.perform(post("/posts/new")
                        .param("name", "testName")
                        .param("description", "testDesc")
                        .param("imageURL","https://cdn.dlcompare.com/others_jpg.jpg")
                        .param("tags","tag1,tag2,tag3"))
                .andExpect(redirectedUrl("/"));

        Post savedPost = postService.findAll().getLast();
        assertEquals("testName",savedPost.getName());
        assertEquals("testDesc",savedPost.getDescription());
        assertEquals("https://cdn.dlcompare.com/others_jpg.jpg",savedPost.getImageURL());


        String savedTagsString = savedPost.getTags().stream().map(tag -> tag.getName()).collect(Collectors.joining(","));

        assertEquals("tag1,tag2,tag3",savedTagsString);
    }

    @Test
    void updatePost_shouldUpdateExistedPostAndRedirectToPost() throws Exception {
        Post beforeUpdatePost = postService.findAll().getLast();
        String beforeUpdatedTagsString = beforeUpdatePost.getTags().stream().map(tag -> tag.getName()).collect(Collectors.joining(","));
        mockMvc.perform(post("/posts/update/" + beforeUpdatePost.getId())
                        .param("name", "updated_Name")
                        .param("description", "updated_Desc")
                        .param("imageURL","https://updated_url.jpg")
                        .param("tags","updated_tag,updated_tag2,updated_tag3"))
                .andExpect(redirectedUrl("/posts/" + beforeUpdatePost.getId()));

        Post updatedPost = postService.getById(beforeUpdatePost.getId());
        assertEquals("updated_Name",updatedPost.getName());
        assertEquals("updated_Desc",updatedPost.getDescription());
        assertEquals("https://updated_url.jpg",updatedPost.getImageURL());

        String updatedTagsString = updatedPost.getTags().stream().map(tag -> tag.getName()).collect(Collectors.joining(","));

        assertEquals("updated_tag,updated_tag2,updated_tag3",updatedTagsString);

        assertEquals(beforeUpdatePost.getId(), updatedPost.getId());
        assertNotEquals(beforeUpdatePost.getName(),updatedPost.getName());
        assertNotEquals(beforeUpdatePost.getDescription(),updatedPost.getDescription());
        assertNotEquals(beforeUpdatedTagsString,updatedTagsString);
    }


}
