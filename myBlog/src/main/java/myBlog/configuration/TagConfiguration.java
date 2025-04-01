package myBlog.configuration;

import myBlog.model.Pagination;
import myBlog.model.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TagConfiguration {
    @Bean
    public Tag selectedTag() {
        Tag selectedTag = new Tag();
        selectedTag.setName("");
        return selectedTag;
    }
}
