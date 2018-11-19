package org.activemq.transaction.dao;

import org.activemq.transaction.model.Event;
import org.activemq.transaction.proxy.ServiceProxy;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseEventDao extends JdbcDaoSupport {

    @Autowired
    private ServiceProxy serviceProxy;

    public int insert(Event event) {
        try {
            String sql = "insert into event(type, progress, content, create_time, update_time) values(?,?,?,?,?)";
            long timestamp = serviceProxy.getTimestamp();
            return getJdbcTemplate().update(sql, new Object[]{event.getType(), event.getProgress(), event.getContent(), timestamp, timestamp});
        } catch (DataAccessException ex) {
            log.warn("insert into event failed, account:{}", event, ex);
            return 0;
        }
    }

    public List<Event> getByProcess(int type, int progress) {
        List<Event> result = new ArrayList<>();
        // 一次最多查询20条消息
        String sql = "select id, type, progress, content from event where type=? and progress= ? order by " +
                "create_time desc limit 20";

        try {
            List<Map<String, Object>> events = getJdbcTemplate().queryForList(sql, new Object[]{type, progress});
            if (CollectionUtils.isEmpty(events)) {
                return result;
            }

            events.forEach(map -> {
                Event event = new Event()
                        .setId((Long) map.get("id"))
                        .setType((Integer) map.get("type"))
                        .setProgress((Integer) map.get("progress"))
                        .setContent((String) map.get("content"));
                result.add(event);
            });
            return result;
        } catch (DataAccessException ex) {
            log.warn("query for list failed, type:{}, progress:{}", type, progress, ex);
            return null;
        }
    }

    public int updateProgress(long id, int progress) {
        try {
            String sql = "update event set progress = ?, update_time = ? where id = ?";
            Object[] params = new Object[]{progress, serviceProxy.getTimestamp(), id};
            return getJdbcTemplate().update(sql, params);
        } catch (DataAccessException ex) {
            log.warn("update progress failed, event id:{}", id, ex);
            return 0;
        }
    }
}
