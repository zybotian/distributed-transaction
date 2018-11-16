package org.activemq.transaction.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tianbo
 * @date 2018-11-16 Friday 14:59
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    SUCCESS(10000, "success", "成功"),

    /**
     * 通用错误码
     */
    INTERNAL_SERVICE_ERROR(10001001, "internal service error", "内部错误"),
    PARTNER_ERROR(10001002, "partner error", "合作方错误"),

    /**
     * 数据库相关错误码
     */
    OBJECT_NOT_EXISTS(10002001, "object not exists", "对象不存在"),

    /**
     * 参数校验类
     */
    INVALID_PARAM(10003001, "invalid param", "无效参数"),

    // 添加其他error code
    ;

    private Integer code;
    private String msg;
    private String desc;
}
