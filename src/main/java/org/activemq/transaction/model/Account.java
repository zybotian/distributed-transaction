package org.activemq.transaction.model;

import lombok.*;

/**
 * @author tianbo
 * @date 2018-11-16 Friday 15:43 钱包账户,额度扣减方
 */
@ToString(exclude = {"createTime", "updateTime"})
@Builder
@Data
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
