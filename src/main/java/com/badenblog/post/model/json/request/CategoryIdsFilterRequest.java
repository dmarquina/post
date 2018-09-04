package com.badenblog.post.model.json;

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
