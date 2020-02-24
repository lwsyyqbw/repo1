package com.ithiema;

import com.itheima.dao.CustomerDao;
import com.itheima.dao.LinkManDao;
import com.itheima.domain.Customer;
import com.itheima.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ObjectToFind {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private LinkManDao linkManDao;

    /**
     * 使用的是延迟加载 可以配置立即加载
     * 一对多 使用延迟加载
     */
    @Test
    @Transactional
    public void testFind(){
        Customer customer = customerDao.findOne(1L);
        Set<LinkMan> linkMans = customer.getLinkMans();
        for (LinkMan linkMan : linkMans) {
            System.out.println(linkMan);
        }
    }

    /**
     * 多对一使用 立即加载
     */
    @Test
    @Transactional
    public void testFind1(){
        LinkMan linkMan = linkManDao.findOne(1L);
        Customer customer = linkMan.getCustomer();
        System.out.println(customer);
    }

    /**
     * 使用spec
     */
    @Test
    @Transactional
    public void testFind2(){
        Specification<LinkMan> spec=new Specification<LinkMan>() {
            @Override
            public Predicate toPredicate(Root<LinkMan> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //root创建多表连接发的方式   Join<Customer, LinkMan> 连接表的对应实体类
                Join<Customer, LinkMan> join = root.join("customer", JoinType.INNER);
                Path<Object> custName = join.get("custName");
                Predicate predicate = criteriaBuilder.like(custName.as(String.class), "天猫%");
                return predicate;
            }
        };

        List<LinkMan> linkManList = linkManDao.findAll(spec);
        for (LinkMan linkMan : linkManList) {
            System.out.println(linkMan);
        }
    }
}
