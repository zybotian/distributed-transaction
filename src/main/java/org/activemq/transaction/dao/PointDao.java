package org.activemq.transaction.dao;

import org.activemq.transaction.model.Point;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class PointDao extends JdbcDaoSupport {

    public String insert(Point point) {
        return null;
    }
}
