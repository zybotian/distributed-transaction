package org.activemq.transaction.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tianbo
 * @date 2018-11-19 Monday 17:01
 */
@Data
@Accessors(chain = true)
public class EventContent {
    private String accountId;
    private int point;
}
