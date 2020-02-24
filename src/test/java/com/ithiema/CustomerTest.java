package com.ithiema;

import com.itheima.dao.CustomerDao;
import com.itheima.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CustomerTest {
    @Autowired
    private CustomerDao customerDao;
/**
 * T findOne(Specification<T> spec);  //查询单个对象
 * root：查询的根对象（查询的任何属性都可以从根对象中获取）
 CriteriaQuery：顶层查询对象，自定义查询方式（了解：一般不用）
 CriteriaBuilder：查询的构造器，封装了很多的查询条件
 Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb); //封装查询条件
 */
    @Test
    public void findOneTest(){
        Specification<Customer> spec=new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> custName = root.get("custName");
                Predicate predicate = criteriaBuilder.equal(custName, "淘宝");
                return predicate;
            }
        };
        Customer customer = customerDao.findOne(spec);
        System.out.println(customer);
    }
    /**
     * List<T> findAll(Specification<T> spec);  //查询列表
     *
     //查询全部，分页
     //pageable：分页参数
     //返回值：分页pageBean（page：是springdatajpa提供的）
     Page<T> findAll(Specification<T> spec, Pageable pageable);
     */
    @Test
    public void testFindAll(){
        Specification<Customer> spec=new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> custIndustry = root.get("custIndustry");
                //使用like  模糊查询  需要指定查询条件的 类型字节码
              //  Predicate predicate = criteriaBuilder.like(custIndustry.as(String.class), "电商%");
                //不能模糊匹配的
                Predicate predicate = criteriaBuilder.notLike(custIndustry.as(String.class), "电商%");
                return predicate;
            }
        };
        /**
         * 分页操作  开始页码  每页的条数
         */
        Pageable pageable=new PageRequest(0,2);
        Page<Customer> customers = customerDao.findAll(spec, pageable);
        /*for (Customer customer : customers) {
            System.out.println(customer);
        }*/
        System.out.println(customers.getTotalPages());
        System.out.println(customers.getTotalElements());
        List<Customer> content = customers.getContent();
        for (Customer customer : content) {
            System.out.println(customer);
        }
    }
    /**
     *
     //查询列表
     //Sort：排序参数
     List<T> findAll(Specification<T> spec, Sort sort);
     */
    @Test
    public void testFindAllSort(){
        Specification<Customer> spec=new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> custId = root.get("custId");
                //除了equal精准匹配都需要 使用as 指定条件的类型 大于  greater than
//                Predicate predicate = criteriaBuilder.gt(custId.as(Long.class), 1L);
                //小于 less than
//                Predicate predicate = criteriaBuilder.lt(custId.as(Long.class), 5L);
                //大于等于   greaterThanOrEqualTo
              //  Predicate predicate = criteriaBuilder.ge(custId.as(Long.class), 2L);
                //小于等于   lessThanOrEqualTo
                Predicate predicate = criteriaBuilder.le(custId.as(Long.class), 5L);

                return predicate;
            }
        };
        //指定排序方式  和排序的属性
        Sort sort=new Sort(Sort.Direction.DESC,"custId");
        List<Customer> customerList = customerDao.findAll(spec, sort);
        for (Customer customer : customerList) {

            System.out.println(customer);
        }
    }

    /**
     * long count(Specification<T> spec);//统计查询
     */
    @Test
    public void testCount(){
        Specification<Customer> spec=new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> custIndustry = root.get("custIndustry");
                Predicate predicate = criteriaBuilder.like(custIndustry.as(String.class), "电商%");
                return predicate;
            }
        };
        long count = customerDao.count(spec);
        System.out.println("行业为电商的有 "+count);
    }
}
