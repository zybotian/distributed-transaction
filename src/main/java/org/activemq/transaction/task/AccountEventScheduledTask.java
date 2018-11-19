package org.activemq.transaction.task;

import org.activemq.transaction.model.Event;
import org.activemq.transaction.service.AccountEventService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tianbo
 * @date 2018-11-19 Monday 17:05
 */
@Service
@Slf4j
public class AccountEventScheduledTask implements ScheduledTask {

    @Autowired
    private AccountEventService accountEventService;

    @Override
    @Scheduled(cron = "*/5 * * * * *")
    public void process() {
        // 查询所有type为account_new,progress为new的event
        List<Event> newEventList = accountEventService.getNewEventList();
        log.info("get account event list:{}", newEventList);
        if (CollectionUtils.isEmpty(newEventList)) {
            return;
        }

        // 发送消息到topic
        for (Event event : newEventList) {
            accountEventService.processEvent(event);
        }
    }
}
