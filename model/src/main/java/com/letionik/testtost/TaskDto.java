package com.letionik.testtost;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private String pictureUrl;
    private TaskType type;

    public Long getId() {
        return id;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
