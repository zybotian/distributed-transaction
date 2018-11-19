package org.activemq.transaction.dao;

import org.activemq.transaction.model.Point;
import org.activemq.transaction.proxy.ServiceProxy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author tianbo
 * @date 2018-11-19 Monday 09:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class PointDaoTest {

    @Autowired
    private PointDao dao;

    @Autowired
    private ServiceProxy serviceProxy;

    @Test
    public void testInsert() throws Exception {
        Point point = new Point()
                .setAccountId("test-" + serviceProxy.getId())
                .setPoint(1000);
        int inserted = dao.insert(point);
        Assert.assertTrue(inserted > 0);
    }
}
