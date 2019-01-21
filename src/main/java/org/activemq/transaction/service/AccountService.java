package org.activemq.transaction.service;

import com.alibaba.fastjson.JSON;

import org.activemq.transaction.constant.EventProgress;
import org.activemq.transaction.constant.EventType;
import org.activemq.transaction.dao.AccountDao;
import org.activemq.transaction.exception.BusinessException;
import org.activemq.transaction.exception.ErrorCode;
import org.activemq.transaction.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AccountEventService accountEventService;

    /**
     * 提供給外界服务调用的service层接口,外界只需要调用这一个接口即可
     * @comment 本地数据库事务可以保证插入account和插入event全部成功或者全部失败
     */
    @Transactional(rollbackFor = BusinessException.class)
    public void newAccount(Account account) throws BusinessException {
        // 往db_account数据库的account表插入新纪录
        int insertAccount = accountDao.insert(account);
        if (insertAccount <= 0) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVICE_ERROR);
        }
        log.info("insert account success, account:{}", account);

        EventContent eventContent = EventContent.builder()
                .point(account.getBalance())
                .accountId(account.getId())
                .build();
        Event event = Event.builder()
                .type(EventType.NEW_ACCOUNT.getCode())
                .progress(EventProgress.NEW.getCode())
                .content(JSON.toJSONString(eventContent))
                .build();
        // 往db_account数据库的event表插入新记录
        int insertEvent = accountEventService.saveEvent(event);
        if (insertEvent <= 0) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVICE_ERROR);
        }
        log.info("insert account event success, event:{}", event);
    }
}
