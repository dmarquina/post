package com.badenblog.post.business;

import com.badenblog.post.model.json.response.UserSchedulesResponse;
import io.reactivex.Observable;

public interface ScheduleService {
    Observable<UserSchedulesResponse> userSchedules(String userId);
}
