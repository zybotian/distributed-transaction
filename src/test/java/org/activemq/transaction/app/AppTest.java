package org.activemq.transaction.app;

import org.activemq.transaction.model.Account;
import org.activemq.transaction.proxy.ServiceProxy;
import org.activemq.transaction.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author tianbo
 * @date 2018-11-19 Monday 17:20
 */
public class AppTest {

    private ApplicationContext applicationContext;
    private ServiceProxy serviceProxy;

    @Before
    public void setup() {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext-test.xml");
        serviceProxy = new ServiceProxy();
    }

    @Test
    public void test() throws Exception {
        AccountService accountService = (AccountService) applicationContext.getBean("accountService");
        Account account = new Account()
                .setId(serviceProxy.getId())
                .setBalance(1000)
               ;

        accountService.newAccount(account);

        Thread.sleep(10000);
    }
}
