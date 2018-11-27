package org.activemq.transaction.service;

import org.activemq.transaction.constant.EventProgress;
import org.activemq.transaction.constant.EventType;
import org.activemq.transaction.dao.AccountEventDao;
import org.activemq.transaction.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;
import javax.jms.*;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountEventService {

    @Autowired
    private AccountEventDao accountEventDao;

    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;

    @Resource(name = "topicDistributedTransaction")
    private Destination topic;

    public int saveEvent(Event event) {
        if (event == null) {
            return 0;
        }
        return accountEventDao.insert(event);
    }

    public List<Event> getNewEventList() {
        return accountEventDao.getByProgress(EventType.NEW_ACCOUNT.getCode(), EventProgress.NEW.getCode());
    }


    public void processEvent(Event event) {
        if (event == null) {
            return;
        }
        // 发送到指定topic
        if (event.getType() == EventType.NEW_ACCOUNT.getCode()
                && event.getProgress() == EventProgress.NEW.getCode()) {
            jmsTemplate.send(topic, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    log.info("send message:{}", event);
                    return session.createTextMessage(event.getContent());
                }
            });
            // 修改event的progress为published,防止下次task重复扫描
            accountEventDao.updateProgress(event.getId(), EventProgress.PUBLISHED.getCode());
            log.info("update account event progress,event id:{}, old progress:{}, new progress:{}", event.getId(),
                    EventProgress.findByCode(event.getProgress()), EventProgress.PUBLISHED);
        }
    }
}
