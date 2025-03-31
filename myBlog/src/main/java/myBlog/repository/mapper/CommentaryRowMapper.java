package myBlog.repository.mapper;

import myBlog.model.Commentary;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CommentaryRowMapper implements RowMapper<Commentary> {

    @Override
    public Commentary mapRow(ResultSet rs, int rowNum) throws SQLException {
        Commentary comment = new Commentary();
        comment.setId(rs.getLong("id"));
        comment.setText(rs.getString("text"));
        // Извлечение TIMESTAMP и преобразование в LocalDateTime
        comment.setCreateDateTime(rs.getObject("createDateTime", LocalDateTime.class));

        return comment;
    }
}
