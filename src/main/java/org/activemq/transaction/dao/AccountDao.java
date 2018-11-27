package org.activemq.transaction.dao;

import org.activemq.transaction.model.Account;
import org.apache.ibatis.annotations.Insert;

public interface AccountDao {

    String TABLE = "account";
    String COLUMN_VALUES = "#{id}, #{balance}, unix_timestamp(), unix_timestamp()";

    @Insert("insert into " + TABLE + " values(" + COLUMN_VALUES + ")")
    int insert(Account account);
}
