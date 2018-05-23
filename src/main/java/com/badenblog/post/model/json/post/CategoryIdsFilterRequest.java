package com.badenblog.post.model.json.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CategoryIdsFilterRequest {
    private List<String> categoryIds;
}
