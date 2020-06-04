package server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.service.VoteService;

import java.util.Date;
@Configuration
@EnableScheduling
public class ScheduledTasks {
//    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private VoteService voteService;

    @Scheduled(fixedRate = 60000)
    public void reportCurrentTime() {
//        voteService.pastDay();
    }
}
