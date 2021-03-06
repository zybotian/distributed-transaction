package org.activemq.transaction.model;

import lombok.*;

/**
 * @author tianbo
 * @date 2018-11-16 Friday 16:17
 */
@ToString(exclude = {"createTime", "updateTime"})
@Builder
@Data
public class Event {
    // 自增主键
    private long id;
    // 事件类型
    private int type;
    // 事件进度
    private int progress;
    // 事件内容
    private String content;
    // 创建时间
    private long createTime;
    // 更新时间
    private long updateTime;
}
