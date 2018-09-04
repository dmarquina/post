package com.badenblog.post.controller;

import com.badenblog.post.business.ScheduleService;
import com.badenblog.post.model.json.response.UserSchedulesResponse;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Observable<UserSchedulesResponse> userSchedules(@PathVariable String userId) {
        return scheduleService.userSchedules(userId);
    }
}
