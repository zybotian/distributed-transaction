package org.activemq.transaction.dao;

import org.activemq.transaction.model.Account;
import org.activemq.transaction.proxy.ServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class AccountDao extends JdbcDaoSupport {

    @Autowired
    private ServiceProxy serviceProxy;

    public int insert(Account account) {
        try {
            String sql = "insert into account values(?,?,?,?)";
            long timestamp = serviceProxy.getTimestamp();
            return getJdbcTemplate().update(sql, new Object[]{account.getId(), account.getBalance(), timestamp, timestamp});
        } catch (DataAccessException ex) {
            log.warn("insert into account failed, account:{}", account, ex);
            return 0;
        }
    }
}
