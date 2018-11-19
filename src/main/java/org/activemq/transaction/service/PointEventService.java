package org.activemq.transaction.service;

import com.alibaba.fastjson.JSON;

import org.activemq.transaction.constant.EventProgress;
import org.activemq.transaction.constant.EventType;
import org.activemq.transaction.dao.PointEventDao;
import org.activemq.transaction.exception.BusinessException;
import org.activemq.transaction.exception.ErrorCode;
import org.activemq.transaction.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PointEventService {

    @Resource
    private PointEventDao pointEventDao;

    @Resource
    private PointService pointService;

    public int saveEvent(Event event) {
        if (event == null) {
            return 0;
        }
        return pointEventDao.insert(event);
    }

    public List<Event> getPublishedEventList() {
        return pointEventDao.getByProcess(EventType.INC_POINT.getCode(), EventProgress.PUBLISHED.getCode());
    }

    @Transactional(rollbackFor = BusinessException.class)
    public void processEvent(Event event) throws BusinessException {
        if (event == null) {
            return;
        }
        if ((EventProgress.PUBLISHED.getCode() == event.getProgress())
                && (EventType.INC_POINT.getCode() == event.getType())) {
            EventContent eventContent = JSON.parseObject(event.getContent(), EventContent.class);
            Point point = new Point()
                    .setAccountId(eventContent.getAccountId())
                    .setPoint(eventContent.getPoint());
            int insertPoint = pointService.savePoint(point);
            if (insertPoint <= 0) {
                log.warn("save point failed:{}", point);
                throw new BusinessException(ErrorCode.INTERNAL_SERVICE_ERROR);
            }
            log.info("save point success:{}", point);

            int updateProcess = pointEventDao.updateProgress(event.getId(), EventProgress.PROCESSED.getCode());
            if (updateProcess <= 0) {
                log.info("update event failed, event id:{}, old progress:{}, new progress:{}", event.getId(),
                        EventProgress.findByCode(event.getProgress()), EventProgress.PROCESSED);
                throw new BusinessException(ErrorCode.INTERNAL_SERVICE_ERROR);
            }
            log.info("update event success, event id:{}, old progress:{}, new progress:{}", event.getId(),
                    EventProgress.findByCode(event.getProgress()), EventProgress.PROCESSED);
        }
    }
}
