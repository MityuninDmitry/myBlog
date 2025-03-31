package myBlog.configuration;

import myBlog.model.Pagination;
import org.h2.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class PaginationConfiguration {
    @Bean
    public Pagination pagination() {
        Pagination pagination = new Pagination(10,0);
        return pagination;
    }
}
