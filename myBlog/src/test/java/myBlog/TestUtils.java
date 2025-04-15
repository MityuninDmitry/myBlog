package myBlog;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.time.LocalDateTime;

public class TestUtils {

    public static void deleteAllPostsFromDB(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("delete from Post");
    }
    public static void deleteAllCommentaryFromDB(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("DELETE FROM Commentary");
    }
    public static void deleteAllTagsFromDB(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("DELETE FROM Tag");
    }

    public static void insertPostsToDB(JdbcTemplate jdbcTemplate, int count) {
        for (int i = 0; i < count; i++) {
            jdbcTemplate.execute("INSERT INTO Post (name, description, likeCounter, imageURL, createDateTime) VALUES ('Post 1', 'Description', 10, 'https://cdn.dlcompare.com/others_jpg/upload/news/image/v-cyberpunk-2077-mojno-igrat-kak-v-gta-no-ne-bez-posledstviy-image-116915b0a.jpeg.webp', CURRENT_TIMESTAMP);");
        }
    }

    public static void insertPostsToDBWithComments(JdbcTemplate jdbcTemplate, int count) {
        for (int i = 0; i < count; i++) {

            String postQuery = "INSERT INTO Post (name, description, likeCounter, imageURL, createDateTime) values(?, ?, ?, ?, ?)";
            // Создаём KeyHolder для хранения сгенерированного ключа
            KeyHolder keyHolder = new GeneratedKeyHolder();
            final long id = i;
            // Выполняем запрос с использованием PreparedStatement
            jdbcTemplate.update(connection -> {
                var ps = connection.prepareStatement(postQuery, new String[]{"id"}); // Указываем, что хотим вернуть столбец "id"
                ps.setString(1, "Post " + id);
                ps.setString(2, "someDesc");
                ps.setInt(3, 0);
                ps.setString(4, "https://someImage.jpg");
                ps.setObject(5, LocalDateTime.now());
                return ps;
            }, keyHolder);

            String query = "insert into commentary(post_id, text, createDateTime) values(?, ?, ?)";
            jdbcTemplate.update(query,
                    keyHolder.getKey().longValue(), "SomeCommentaryText " + i, LocalDateTime.now());
        }
    }

    public static void insertPostsToDBWithTags(JdbcTemplate jdbcTemplate, int count) {
        for (int i = 0; i < count; i++) {
            String postQuery = "INSERT INTO Post (name, description, likeCounter, imageURL, createDateTime) values(?, ?, ?, ?, ?)";
            // Создаём KeyHolder для хранения сгенерированного ключа
            KeyHolder keyHolder = new GeneratedKeyHolder();
            final long id = i;
            // Выполняем запрос с использованием PreparedStatement
            jdbcTemplate.update(connection -> {
                var ps = connection.prepareStatement(postQuery, new String[]{"id"}); // Указываем, что хотим вернуть столбец "id"
                ps.setString(1, "Post " + id);
                ps.setString(2, "someDesc");
                ps.setInt(3, 0);
                ps.setString(4, "https://someImage.jpg");
                ps.setObject(5, LocalDateTime.now());
                return ps;
            }, keyHolder);

            String query = "insert into tag(post_id, name) values(?, ?)";
            jdbcTemplate.update(query, keyHolder.getKey().longValue(), "Post"+i);
        }
    }
    public static void insertPostsToDBWithDoubleTags(JdbcTemplate jdbcTemplate, int count) {
        for (int i = 0; i < count; i++) {
            String postQuery = "INSERT INTO Post (name, description, likeCounter, imageURL, createDateTime) values(?, ?, ?, ?, ?)";
            // Создаём KeyHolder для хранения сгенерированного ключа
            KeyHolder keyHolder = new GeneratedKeyHolder();
            final long id = i;
            // Выполняем запрос с использованием PreparedStatement
            jdbcTemplate.update(connection -> {
                var ps = connection.prepareStatement(postQuery, new String[]{"id"}); // Указываем, что хотим вернуть столбец "id"
                ps.setString(1, "Post " + id);
                ps.setString(2, "someDesc");
                ps.setInt(3, 0);
                ps.setString(4, "https://someImage.jpg");
                ps.setObject(5, LocalDateTime.now());
                return ps;
            }, keyHolder);

            String query = "insert into tag(post_id, name) values(?, ?)";
            jdbcTemplate.update(query, keyHolder.getKey().longValue(), "Tag"+keyHolder.getKey().longValue());
            jdbcTemplate.update(query, keyHolder.getKey().longValue(), "Tag"+keyHolder.getKey().longValue());
        }
    }


}
