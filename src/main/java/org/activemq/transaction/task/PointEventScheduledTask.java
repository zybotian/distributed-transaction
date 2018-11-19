package org.activemq.transaction.task;

import org.activemq.transaction.dao.PointDao;
import org.activemq.transaction.exception.BusinessException;
import org.activemq.transaction.model.Event;
import org.activemq.transaction.service.PointEventService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tianbo
 * @date 2018-11-19 Monday 17:06
 */
@Service
@Slf4j
public class PointEventScheduledTask implements ScheduledTask {

    @Autowired
    private PointEventService pointEventService;

    @Autowired
    private PointDao pointDao;

    @Override
    @Scheduled(cron = "*/5 * * * * *")
    public void process() {
        // 查询所有status为new的event
        List<Event> newEventList = pointEventService.getPublishedEventList();
        log.info("get published events:{}", newEventList);
        if (CollectionUtils.isEmpty(newEventList)) {
            return;
        }

        for (Event event : newEventList) {
            try {
                pointEventService.processEvent(event);
            } catch (BusinessException ex) {
                log.warn("point event service process event failed", ex);
            }
        }
    }
}
