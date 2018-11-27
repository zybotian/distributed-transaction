package org.activemq.transaction.dao;

import org.activemq.transaction.model.Event;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BaseEventDao {

    String INSERT_COLUMNS = "type, progress, content, create_time, update_time";
    String INSERT_VALUES = "#{type}, #{progress}, #{content}, unix_timestamp(), unix_timestamp()";

    String SELECT_COLUMNS = "id, type, progress, content, create_time as createTime, update_time as updateTime";
    String TABLE = "event";

    @Insert("insert into " + TABLE + " ( " + INSERT_COLUMNS + " ) " + " values ( " + INSERT_VALUES + " ) ")
    int insert(Event event);

    @Select("select " + SELECT_COLUMNS + " from " + TABLE + " where type=#{type} and progress=#{progress} order by " +
            "create_time desc limit 20")
    List<Event> getByProgress(@Param("type") int type, @Param("progress") int progress);

    @Update("update event set progress = #{progress}, update_time = unix_timestamp() where id = #{id}")
    int updateProgress(@Param("id") long id, @Param("progress") int progress);
}
