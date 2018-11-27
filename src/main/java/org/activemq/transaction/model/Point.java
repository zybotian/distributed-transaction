package org.activemq.transaction.model;

import lombok.*;

/**
 * @author tianbo
 * @date 2018-11-16 Friday 15:43 积分表, 额度增加方
 */
@ToString(exclude = {"updateTime", "createTime"})
@Builder
@Data
public class Point {
    // 主键id
    private long id;
    // 积分数
    private int point;
    // 关联的用户id
    private String accountId;
    // 创建时间
    private long createTime;
    // 更新时间
    private long updateTime;
}
