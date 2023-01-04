package com.muates.userservice.scheduler;

import com.muates.userservice.scheduler.service.UserRemoveService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSchedulerService {

    private final UserRemoveService userRemoveService;

    @Scheduled(cron = "${mua.scheduler.cron}")
    @SchedulerLock(
            name = "userRemoveTask",
            lockAtLeastForString = "PT5M",
            lockAtMostForString = "PT14M"
    )
    public void scheduler() {
        userRemoveService.removeUser();
    }
}
