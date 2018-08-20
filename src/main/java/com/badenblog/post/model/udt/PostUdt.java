package com.badenblog.post.model.udt;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@UserDefinedType("post")
public class PostUdt {

    @Column("id")
    private UUID id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("min_age")
    private int minAge;

    @Column("creation_date")
    private LocalDate creationDate;

    @Column("categories")
    private Set<String> categories = new HashSet<>();

    @Column("materials")
    private Set<String> materials = new HashSet<>();

    @Column("uid")
    private String uid;

    @Column("user_displayname")
    private String userDisplayName;
}
