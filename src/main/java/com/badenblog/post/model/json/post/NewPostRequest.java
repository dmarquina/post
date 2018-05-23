package com.badenblog.post.model.json.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class NewPostRequest {
    private String name;
    private String description;
    private int minAge;
    private List<String> materials;
    private List<NewPostCategoryRequest> categories;
}
