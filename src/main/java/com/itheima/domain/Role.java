package com.itheima.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "sys_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "role_age")
    private Integer roleAge;
/**
 * 多对多
 */
  // @ManyToMany(mappedBy = "roles")
   @ManyToMany(targetEntity = User.class)
   @JoinTable(name = "sys_user_role",joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "role_id")},
   inverseJoinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "user_id")})
   private Set<User> users=new HashSet<>();

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleAge() {
        return roleAge;
    }

    public void setRoleAge(Integer roleAge) {
        this.roleAge = roleAge;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
