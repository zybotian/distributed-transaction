package org.activemq.transaction.service;

import org.activemq.transaction.dao.PointDao;
import org.activemq.transaction.model.Point;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PointService {

    @Resource
    private PointDao dao;

    public int savePoint(Point point) {
        if (point == null) {
            return 0;
        }
        return dao.insert(point);
    }

}
