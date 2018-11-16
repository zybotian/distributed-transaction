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

    AMOUNT_INC(1, "AMOUNT_INC", "amount increase"),
    AMOUNT_DEC(2, "AMOUNT_DEC", "amount decrease"),
    ;

    private int code;
    private String value;
    private String desc;
}
