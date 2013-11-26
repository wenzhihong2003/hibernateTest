package model;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * User: wenzhihong
 * Date: 11/13/13
 * Time: 10:59 AM
 */
public abstract class User {
    private Long id;

    private String name;

    private Integer age;

    private Boolean man;

    private Boolean used = Boolean.TRUE;

    private int version;

    private Grade grade = Grade.primary;

    private Set<UserRole> userRoles = Sets.newHashSet();

    public User() {
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getMan() {
        return man;
    }

    public void setMan(Boolean man) {
        this.man = man;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void addUserRole(UserRole userRole) {
        if (userRole.getUser() != null) {
            userRole.getUser().getUserRoles().remove(userRole);
        }
        if (userRole.getRole() != null) {
            userRole.getRole().getUserRoles().remove(userRole);
        }

        userRoles.add(userRole);
        userRole.setUser(this);
    }


}
