package com.badenblog.post.model.json;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class NewPostCategoryRequest {
    private String id;
    private String name;
}
