package org.activemq.transaction.dao;

import org.activemq.transaction.model.Point;
import org.activemq.transaction.proxy.ServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class PointDao extends JdbcDaoSupport {

    @Autowired
    private ServiceProxy serviceProxy;

    public int insert(Point point) {
        try {
            String sql = "insert into point(point,account_id,create_time,update_time) values(?,?,?,?)";
            long timestamp = serviceProxy.getTimestamp();
            return getJdbcTemplate().update(sql, (PreparedStatement preparedStatement) -> {
                preparedStatement.setInt(1, point.getPoint());
                preparedStatement.setString(2, point.getAccountId());
                preparedStatement.setLong(3, timestamp);
                preparedStatement.setLong(4, timestamp);
            });
        } catch (DataAccessException ex) {
            log.warn("insert into point failed, point:{}", point, ex);
            return 0;
        }
    }
}