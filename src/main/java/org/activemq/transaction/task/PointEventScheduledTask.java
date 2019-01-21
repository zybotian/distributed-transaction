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

    /**
     * @comment 如果定时任务挂了,只需要重启定时任务即可
     * @comment 数据库本地事务保证,修改event状态和增加积分两个操作要么全部成功,要么全部失败
     * @comment 如果同时失败了,下一次定时任务依然会扫描到,可以重入
     */
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
