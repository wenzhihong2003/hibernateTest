package model;

/**
 * User: wenzhihong
 * Date: 11/13/13
 * Time: 12:54 PM
 */
public class UserRole {
    private Long id;

    private User user;

    private Role role;

    public UserRole() {
    }

    public UserRole(User user, Role role) {
        this.user = user;
        //this.user.getUserRoles().add(this);
        this.role = role;
        //this.role.getUserRoles().add(this);
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
