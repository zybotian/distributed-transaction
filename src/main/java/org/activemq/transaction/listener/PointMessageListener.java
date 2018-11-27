package org.activemq.transaction.listener;


import com.alibaba.fastjson.JSON;

import org.activemq.transaction.constant.EventProgress;
import org.activemq.transaction.constant.EventType;
import org.activemq.transaction.model.Event;
import org.activemq.transaction.model.EventContent;
import org.activemq.transaction.service.PointEventService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.jms.*;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tianbo
 * @date 2018-11-16 Friday 18:09
 */
@Slf4j
public class PointMessageListener implements MessageListener {

    @Resource
    private PointEventService pointEventService;

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                TextMessage txtMsg = (TextMessage) message;
                String eventContentStr = txtMsg.getText();
                log.info("message listener received message:{}", eventContentStr);

                if (StringUtils.isEmpty(eventContentStr)) {
                    log.warn("received empty message, ignore it");
                    return;
                }

                EventContent eventContent = JSON.parseObject(eventContentStr, EventContent.class);
                if (StringUtils.isEmpty(eventContent.getAccountId())) {
                    log.warn("received invalid message, ignore it");
                    return;
                }
                // 收到一条消息,创建一个event并入库
                Event event = Event.builder()
                        .type(EventType.INC_POINT.getCode())
                        .progress(EventProgress.PUBLISHED.getCode())
                        .content(eventContentStr)
                        .build();
                pointEventService.saveEvent(event);
            } catch (JMSException e) {
                log.warn("process message failed", e);

                // 业务补偿消息: 报警,人工介入
            }
        }
    }
}
