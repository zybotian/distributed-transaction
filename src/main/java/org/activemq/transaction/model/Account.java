package org.activemq.transaction.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author tianbo
 * @date 2018-11-16 Friday 15:43 钱包账户,额度扣减方
 */
@Data
@Accessors(chain = true)
@ToString(exclude = {"createTime", "updateTime"})
public class Account {
    // 主键ID
    private String id;
    // 当前余额
    private int balance;
    // 创建时间
    private long createTime;
    // 更新时间
    private long updateTime;
}