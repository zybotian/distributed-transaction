package org.activemq.transaction.service;

import org.activemq.transaction.dao.PointDao;
import org.activemq.transaction.exception.BusinessRuntimeException;
import org.activemq.transaction.exception.ErrorCode;
import org.activemq.transaction.model.Point;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PointService {

    @Resource
    private PointDao dao;

    public String savePoint(Point point) {
        if (point != null) {
            return dao.insert(point);
        } else {
            throw new BusinessRuntimeException(ErrorCode.INVALID_PARAM);
        }
    }

}
