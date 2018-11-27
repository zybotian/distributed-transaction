package org.activemq.transaction.dao;

import org.activemq.transaction.model.Point;
import org.apache.ibatis.annotations.Insert;

public interface PointDao {

    String TABLE = "point";
    String COLUMN_NAMES = "point,account_id,create_time,update_time";
    String COLUMN_VALUES = "#{point},#{accountId},unix_timestamp(),unix_timestamp()";
//    @Autowired
//    private ServiceProxy serviceProxy;

    //    public int insert(Point point) {
//        try {
//            String sql = "insert into point(point,account_id,create_time,update_time) values(?,?,?,?)";
//            long timestamp = serviceProxy.getTimestamp();
//            return getJdbcTemplate().update(sql, (PreparedStatement preparedStatement) -> {
//                preparedStatement.setInt(1, point.getPoint());
//                preparedStatement.setString(2, point.getAccountId());
//                preparedStatement.setLong(3, timestamp);
//                preparedStatement.setLong(4, timestamp);
//            });
//        } catch (DataAccessException ex) {
//            log.warn("insert into point failed, point:{}", point, ex);
//            return 0;
//        }
//    }
    @Insert("insert into " + TABLE + " (" + COLUMN_NAMES + ") values (" + COLUMN_VALUES + ")")
    int insert(Point point);
}