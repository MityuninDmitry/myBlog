package model;

public class Post {
    private long id;
    private String name;
    private String description;
    private Post[] tags;
    private int likeCounter;
    private Commentary[] commentaries;
    private byte[] image;

    public Post(Long id, String name) {
        this.id = id;
        this.name = name;
        likeCounter = 0;
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

    public Post[] getTags() {
        return tags;
    }

    public void setTags(Post[] tags) {
        this.tags = tags;
    }

    public int getLikeCounter() {
        return likeCounter;
    }

    public void setLikeCounter(int likeCounter) {
        this.likeCounter = likeCounter;
    }

    public Commentary[] getCommentaries() {
        return commentaries;
    }

    public void setCommentaries(Commentary[] commentaries) {
        this.commentaries = commentaries;
    }
}
