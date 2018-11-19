package org.activemq.transaction.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author tianbo
 * @date 2018-11-16 Friday 15:43 积分表, 额度增加方
 */
@Data
@Accessors(chain = true)
@ToString(exclude = {"updateTime", "createTime"})
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
