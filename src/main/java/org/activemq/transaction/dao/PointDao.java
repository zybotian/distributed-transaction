package org.activemq.transaction.dao;

import org.activemq.transaction.model.Point;
import org.apache.ibatis.annotations.Insert;

public interface PointDao {

    String TABLE = "point";
    String COLUMN_NAMES = "point,account_id,create_time,update_time";
    String COLUMN_VALUES = "#{point},#{accountId},unix_timestamp(),unix_timestamp()";

    @Insert("insert into " + TABLE + " (" + COLUMN_NAMES + ") values (" + COLUMN_VALUES + ")")
    int insert(Point point);
}