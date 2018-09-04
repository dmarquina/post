package com.badenblog.post.model.udt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Getter
@Setter
@UserDefinedType("post_display")
public class PostDisplayUdt {

    @Column("id")
    private String id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;
}
