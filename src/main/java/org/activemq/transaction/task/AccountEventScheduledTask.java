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

    /**
     * @comment 如果定时任务挂掉了,只需要重启定时任务即可
     * @comment 如果event状态修改失败,消息发送失败,则等待下次定时任务扫描即可重入
     * @comment 如果event状态修改成功但消息发送失败了,可以数据库上线,保证event可以再次被扫到
     * @comment 如果event状态修改失败但消息发送成功了,定时任务下次扫到了event会再次发送给mq,mq收到了重复消息,问题转化为mq如何对消息去重?
     * @comment mq如何去重消息? mq什么都不需要做,只要消息的消费者保证自己的处理逻辑是幂等操作即可
     */
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
