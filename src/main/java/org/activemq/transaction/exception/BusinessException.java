package org.activemq.transaction.exception;

import lombok.AllArgsConstructor;

/**
 * @author tianbo
 * @date 2018-11-16 Friday 15:12
 */
@AllArgsConstructor
public class BusinessException extends Exception {

    private ErrorCode errorCode;

    @Override
    public String toString() {
        return "BusinessException{" +
                "errorCode=" + errorCode +
                '}';
    }
}
