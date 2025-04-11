package myBlog.controller;

import myBlog.service.PaginationService;
import myBlog.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class TagControllerIntegrationTest {

    @Autowired
    private PaginationService paginationService;
    @Autowired
    private TagService tagService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        paginationService.updatePagination(20,4);
    }

    @Test
    void setSelectedTag_FilledName() throws Exception {
        mockMvc.perform(get("/tag/tag1"))
                .andExpect(redirectedUrl("/"));

        assertEquals("tag1",tagService.getSelectedTag().getName());
        assertEquals(20, paginationService.getPagination().getSize());
    }

    @Test
    void setSelectedTagEmpty() throws Exception {
        mockMvc.perform(get("/tag/"))
                .andExpect(redirectedUrl("/"));

        assertEquals("",tagService.getSelectedTag().getName());
        assertEquals(0, paginationService.getPagination().getPage());
        assertEquals(20, paginationService.getPagination().getSize());
    }
}
