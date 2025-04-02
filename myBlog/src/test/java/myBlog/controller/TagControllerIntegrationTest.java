package myBlog.controller;

import myBlog.WebConfiguration;
import myBlog.configuration.TagConfiguration;
import myBlog.service.PaginationService;
import myBlog.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(classes = {WebConfiguration.class, TagConfiguration.class})
@WebAppConfiguration
@TestPropertySource(locations = "classpath:test-application.properties")
public class TagControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private PaginationService paginationService;
    @Autowired
    private TagService tagService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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
