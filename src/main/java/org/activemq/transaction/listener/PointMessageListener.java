package org.activemq.transaction.listener;


import org.activemq.transaction.constant.EventProgress;
import org.activemq.transaction.constant.EventType;
import org.activemq.transaction.exception.BusinessRuntimeException;
import org.activemq.transaction.exception.ErrorCode;
import org.activemq.transaction.model.Event;
import org.activemq.transaction.service.PointEventService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.jms.*;

/**
 * @author tianbo
 * @date 2018-11-16 Friday 18:09
 */
public class PointMessageListener implements MessageListener {

    @Resource
    private PointEventService pointEventService;

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                TextMessage txtMsg = (TextMessage) message;
                String eventContent = txtMsg.getText();
                System.out.println("队列监听器接收到文本消息：" + eventContent);

                if (StringUtils.isNotEmpty(eventContent)) {
                    // 新增事件
                    Event event = new Event();
                    event.setType(EventType.AMOUNT_INC.getCode());
                    event.setProgress(EventProgress.SENT_TO_MQ.getCode());
                    event.setContent(eventContent);
                }
            } catch (JMSException e) {
                // 业务补偿消息
                e.printStackTrace();
            }
        } else {
            throw new BusinessRuntimeException(ErrorCode.INVALID_PARAM);
        }
    }
}
