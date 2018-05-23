package com.badenblog.post.model.json.post.response;

import com.datastax.driver.core.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class HomePostResponse {
    private UUID id;

    private String name;

    private String description;

    private int minAge ;

    private LocalDate creationDate;

    private Set<String> categories = new HashSet<>();

    private Set<String> materials= new HashSet<>();
}
