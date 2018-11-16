package org.activemq.transaction.proxy;

import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author tianbo
 * @date 2018-11-16 Friday 15:58
 */
@Service
public class ServiceProxy {

    /**
     * 获取唯一id
     */
    public String getId() {
        // 这里仅做简单模拟,实际项目中应该调用公司内部的id center服务获取唯一递增id
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取服务器当前时间
     */
    public long getTimestamp() {
        return System.currentTimeMillis();
    }
}
