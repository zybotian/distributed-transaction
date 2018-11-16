package org.activemq.transaction.exception;

import lombok.AllArgsConstructor;

/**
 * @author tianbo
 * @date 2018-11-16 Friday 18:15
 */
@AllArgsConstructor
public class BusinessRuntimeException extends RuntimeException {
    private ErrorCode errorCode;

    @Override
    public String toString() {
        return "BusinessRuntimeException{" +
                "errorCode=" + errorCode +
                '}';
    }
}
