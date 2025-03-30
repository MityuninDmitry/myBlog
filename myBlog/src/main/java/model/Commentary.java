package model;

import java.time.LocalDateTime;

public class Commentary {
    private long id;
    private String text;
    private LocalDateTime createDateTime;

    public Commentary(long id, String text) {
        this.id = id;
        this.text = text;
        createDateTime = LocalDateTime.now();
    }

    public Commentary() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }
}
