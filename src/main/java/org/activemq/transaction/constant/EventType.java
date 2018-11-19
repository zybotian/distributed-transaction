package org.activemq.transaction.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tianbo
 * @date 2018-11-16 Friday 15:28
 */
@AllArgsConstructor
@Getter
public enum EventType {

    NEW_ACCOUNT(1, "NEW_ACCOUNT", "create account"),
    INC_POINT(2, "INC_POINT", "increase point"),
    ;

    private int code;
    private String value;
    private String desc;
}
