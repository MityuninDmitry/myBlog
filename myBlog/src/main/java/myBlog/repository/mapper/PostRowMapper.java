package myBlog.repository.mapper;

import myBlog.model.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PostRowMapper implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        Post post = new Post();
        post.setId(rs.getLong("id"));
        post.setName(rs.getString("name"));
        post.setDescription(rs.getString("description"));
        post.setLikeCounter(rs.getInt("likeCounter"));
        post.setImageURL(rs.getString("imageURL"));

        // Извлечение TIMESTAMP и преобразование в LocalDateTime
        post.setCreateDateTime(rs.getObject("createDateTime", LocalDateTime.class));

        return post;
    }
}
