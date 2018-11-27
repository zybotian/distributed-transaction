package org.activemq.transaction.dao;

import org.activemq.transaction.model.Account;
import org.activemq.transaction.proxy.ServiceProxy;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author tianbo
 * @date 2018-11-20 Tuesday 11:00
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
@Ignore
public class AccountDAOTest {

    @Autowired
    AccountDao accountDao;

    @Autowired
    private ServiceProxy serviceProxy;

    @Test
    public void testInsert() throws Exception {
        Account account = Account.builder()
                .id("paul-test-" + serviceProxy.getId())
                .balance(1000)
                .build();
        int insert = accountDao.insert(account);
        System.out.println(insert);
    }
}
