package com.badenblog.post.model.json.response;

import com.badenblog.post.model.udt.PostDisplayUdt;
import com.datastax.driver.core.LocalDate;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserSchedulesResponse {
    private String name;

    private String applicationDate;

    private List<PostDisplayUdt> posts;
}
