package org.activemq.transaction.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tianbo
 * @date 2018-11-16 Friday 15:28
 */
@AllArgsConstructor
@Getter
public enum EventProgress {

    CREATED(1, "CREATED", "created"),
    SENT_TO_MQ(3, "SENT_TO_MQ", "sent to mq"),
    FINISHED(2, "FINISHED", "finished"),
    ;

    private int code;
    private String value;
    private String desc;
}
