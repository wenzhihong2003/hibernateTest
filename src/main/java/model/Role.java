package model;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * User: wenzhihong
 * Date: 11/13/13
 * Time: 11:01 AM
 */
public class Role {
    private Long id;

    private String name;

    private Set<UserRole> userRoles = Sets.newHashSet();

    public Role() {
    }

    public Role(String name) {
        this.name = name;
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

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public void addUserRole(UserRole userRole) {
        if (userRole.getUser() != null) {
            userRole.getUser().getUserRoles().remove(userRole);
        }
        if (userRole.getRole() != null) {
            userRole.getRole().getUserRoles().remove(userRole);
        }

        userRoles.add(userRole);
        userRole.setRole(this);
    }
}
