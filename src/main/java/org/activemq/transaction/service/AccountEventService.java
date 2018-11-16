package org.activemq.transaction.service;

import org.activemq.transaction.constant.EventProgress;
import org.activemq.transaction.dao.AccountEventDao;
import org.activemq.transaction.exception.BusinessRuntimeException;
import org.activemq.transaction.exception.ErrorCode;
import org.activemq.transaction.model.Event;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class AccountEventService {

    @Resource
    private AccountEventDao userEventDao;

    public int saveEvent(Event event) {
        if (event != null) {
            return userEventDao.insert(event);
        } else {
            throw new BusinessRuntimeException(ErrorCode.INVALID_PARAM);
        }
    }

    public List<Event> getNewEventList() {
        return userEventDao.getByProcess(EventProgress.CREATED.getCode());
    }


}
