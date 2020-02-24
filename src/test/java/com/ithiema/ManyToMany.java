package com.ithiema;

import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.domain.Role;
import com.itheima.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ManyToMany {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Transactional
    @Rollback(value = false)
    @Test
    public void testSave(){
        User user = new User();
        user.setUserName("张三");
        Role role = new Role();
        role.setRoleName("学生");
        role.getUsers().add(user);
        user.getRoles().add(role);
        userDao.save(user);
        roleDao.save(role);
    }
    /**
     * 级联操作  插入
     */
    @Transactional
    @Rollback(value = false)
    @Test
    public void test(){
        User user = new User();
        user.setUserName("张三");
        Role role = new Role();
        role.setRoleName("学生");
        user.getRoles().add(role);
        role.getUsers().add(user);
        //存储一个即可
        userDao.save(user);
    }
    /**
     * 级联删除
     */
    @Transactional
    @Rollback(value = false)
    @Test
    public void testDelete(){
       //查询
        User user = userDao.findOne(1L);
        userDao.delete(user);
    }
}
