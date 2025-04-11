package myBlog.controller;

import myBlog.service.PaginationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class PaginationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @MockitoBean
    private PaginationService paginationService;



    @BeforeEach
    void setUp() {

        jdbcTemplate.execute("delete from Post");
        for (int i = 0; i < 150; i++) {
            jdbcTemplate.execute("INSERT INTO Post (id, name, description, likeCounter, imageURL, createDateTime) VALUES ("+i+",'Post 1', 'Description', 10, 'https://cdn.dlcompare.com/others_jpg/upload/news/image/v-cyberpunk-2077-mojno-igrat-kak-v-gta-no-ne-bez-posledstviy-image-116915b0a.jpeg.webp', CURRENT_TIMESTAMP);");
        }
    }

    public static Stream<Integer> provideSize() {
        return Stream.of(10,20,50);
    }

    @ParameterizedTest
    @MethodSource("provideSize")
    void handlePagination_shouldRedirectAndUpdatePagination(Integer size) throws Exception {
        mockMvc.perform(get("/pagination")
                        .param("size",String.valueOf(size))
                        .param("page","0"))
                .andExpect(redirectedUrl("/"));

        verify(paginationService,times(1)).updatePagination(size,0);
    }


}
