package com.ithiema;

import com.itheima.dao.CustomerDao;
import com.itheima.dao.LinkManDao;
import com.itheima.domain.Customer;
import com.itheima.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OneToMany {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private LinkManDao linkManDao;
    /**
     * 保存
     */
    @Transactional
    @Rollback(value = false)
    @Test
    public void testSave(){
        Customer customer = new Customer();
        LinkMan linkMan = new LinkMan();
        customer.setCustName("淘宝");
        linkMan.setLkmName("小李");
        customer.getLinkMans().add(linkMan);
        linkMan.setCustomer(customer);
        customerDao.save(customer);
        linkManDao.save(linkMan);
    }
    /**
     * 删除操作 级联操作
     */
    @Transactional
    @Rollback(value = false)
    @Test
    public void testDelete(){
        //查询对应的customer
        Customer customer = customerDao.findOne(1L);
        customerDao.delete(customer);
    }
    /**
     * 保存操作 级联操作
     */
    @Transactional
    @Rollback(value = false)
    @Test
    public void test(){
        //查询对应的customer
        Customer customer = new Customer();
        customer.setCustName("小李");
        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("老王");
        //保存一个即可
        customer.getLinkMans().add(linkMan);
        customerDao.save(customer);
    }
}
