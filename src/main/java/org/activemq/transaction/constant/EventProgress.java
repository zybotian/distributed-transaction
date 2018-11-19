package org.activemq.transaction.constant;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tianbo
 * @date 2018-11-16 Friday 15:28
 */
@AllArgsConstructor
@Getter
public enum EventProgress {

    NEW(1, "NEW", "new"),
    PUBLISHED(2, "PUBLISHED", "sent to mq"),
    PROCESSED(3, "PROCESSED", "finished"),
    ;

    private static final Map<Integer, EventProgress> codeMap = new HashMap<>();

    static {
        for (EventProgress eventProgress : EventProgress.values()) {
            codeMap.put(eventProgress.getCode(), eventProgress);
        }
    }

    private int code;
    private String value;
    private String desc;

    public static EventProgress findByCode(int code) {
        return codeMap.get(code);
    }
}
