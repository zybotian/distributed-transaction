package org.activemq.transaction.dao;

import org.activemq.transaction.model.Event;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.*;

public class BaseEventDao extends JdbcDaoSupport {

    public Integer insert(Event event) {
        String sql = "insert into event(type, progress, content, create_time, update_time) values(?,?,?,?,?)";
        Object[] params = new Object[]{event.getType(), event.getProgress(), event.getContent(), event.getCreateTime(),
                event.getUpdateTime()};
        return getJdbcTemplate().update(sql, params);
    }

    public Integer updateProcess(Event event) {
        String sql = "update event set progress = ?, update_time = ? where id = ?";
        Object[] params = new Object[]{event.getId(), event.getUpdateTime(), event.getProgress()};
        return getJdbcTemplate().update(sql, params);
    }

    public List<Event> getByProcess(int progress) {
        List<Event> result = new ArrayList<>();

        String sql = "select id, type, progress, content from event where progress = ?";
        List<Map<String, Object>> events = getJdbcTemplate().queryForList(sql, progress);
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
    }
}
