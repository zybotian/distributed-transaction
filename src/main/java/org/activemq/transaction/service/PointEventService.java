package org.activemq.transaction.service;

import org.activemq.transaction.constant.EventProgress;
import org.activemq.transaction.dao.PointEventDao;
import org.activemq.transaction.exception.BusinessRuntimeException;
import org.activemq.transaction.exception.ErrorCode;
import org.activemq.transaction.model.Event;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class PointEventService {

    @Resource
    private PointEventDao pointEventDao;

    public int saveEvent(Event event) {
        if (event != null) {
            return pointEventDao.insert(event);
        } else {
            throw new BusinessRuntimeException(ErrorCode.INVALID_PARAM);
        }
    }

    public List<Event> getPublishedEventList() {
        return pointEventDao.getByProcess(EventProgress.SENT_TO_MQ.getCode());
    }
}
