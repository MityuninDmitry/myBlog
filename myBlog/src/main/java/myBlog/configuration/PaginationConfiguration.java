package myBlog.configuration;

import myBlog.model.Pagination;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaginationConfiguration {
    @Bean
    public Pagination pagination() {
        Pagination pagination = new Pagination(10,0);
        return pagination;
    }
}
