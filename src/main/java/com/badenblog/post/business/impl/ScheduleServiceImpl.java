package com.badenblog.post.business.impl;

import com.badenblog.post.business.ScheduleService;
import com.badenblog.post.model.entity.ScheduleEntity;
import com.badenblog.post.model.json.response.UserSchedulesResponse;
import com.badenblog.post.repository.ScheduleRepository;
import com.badenblog.post.util.DateUtil;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    ScheduleRepository scheduleRespository;

    @Override
    public Observable<UserSchedulesResponse> userSchedules(String userId) {
        return scheduleRespository.findAllByUserIdOrderByApplicationDateDesc(userId)
                .toObservable()
                .map(this::convertScheduleEntityToResponse);
    }

    private UserSchedulesResponse convertScheduleEntityToResponse(ScheduleEntity scheduleEntity){
        UserSchedulesResponse userSchedulesResponse = new UserSchedulesResponse();
        userSchedulesResponse.setName(scheduleEntity.getName());
        String dateDisplay = DateUtil.getDateStringFromUuid(scheduleEntity.getApplicationDate());
        userSchedulesResponse.setApplicationDate(dateDisplay);
        userSchedulesResponse.setPosts(new ArrayList(scheduleEntity.getPosts()));
        return userSchedulesResponse;
    }

}