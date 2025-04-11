package myBlog.model;

import java.time.LocalDateTime;
import java.util.List;

public class Post {
    private long id;
    private String name;
    private String description;
    private List<Tag> tags;
    private int likeCounter;
    private List<Commentary> commentaries;
    private String imageURL;
    private LocalDateTime createDateTime;

    public Post() {

    }
    public Post(long id, String name, String description, List<Tag> tags, int likeCounter, List<Commentary> commentaries, String imageURL, LocalDateTime createDateTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.likeCounter = likeCounter;
        this.commentaries = commentaries;
        this.imageURL = imageURL;
        this.createDateTime = createDateTime;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public int getLikeCounter() {
        return likeCounter;
    }

    public void setLikeCounter(int likeCounter) {
        this.likeCounter = likeCounter;
    }

    public List<Commentary> getCommentaries() {
        return commentaries;
    }

    public void setCommentaries(List<Commentary> commentaries) {
        this.commentaries = commentaries;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
