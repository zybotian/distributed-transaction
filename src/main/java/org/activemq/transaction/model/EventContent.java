package org.activemq.transaction.model;

import lombok.*;

/**
 * @author tianbo
 * @date 2018-11-19 Monday 17:01
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EventContent {
    private String accountId;
    private int point;
}
