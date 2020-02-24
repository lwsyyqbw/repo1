package com.itheima.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户实体类
 */
@Entity
@Table(name = "sys_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
  private Long userId;
    @Column(name = "user_name")
  private String userName;
    /**
     * 多对多的关系映射
     * 使用中间表
     */
    // @ManyToMany(targetEntity = Role.class)
    //建立关联表
     //@JoinTable(name = "sys_user_role",joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "user_id")},
   // inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "role_id")})
            //放弃外键维护
    @ManyToMany(mappedBy = "users",cascade = CascadeType.ALL)
    private Set<Role> roles=new HashSet<>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
