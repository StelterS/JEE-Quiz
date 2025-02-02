package com.klopsi.user.view;

import com.klopsi.user.UserService;
import com.klopsi.user.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Named
@ViewScoped
public class UserEdit implements Serializable {
    private UserService service;

    @Getter
    @Setter
    private String newUserRole;

    @Setter
    private User user;

    public User getUser() {
        if(user == null) {
            user = new User();
        }
        List<String> roles = user.getRoles();
        if(roles != null) {
            // check what role does user have
            if(roles.contains(User.Roles.ADMIN)){
                newUserRole = User.Roles.ADMIN;
            }
            else if (roles.contains(User.Roles.MODERATOR)){
                newUserRole = User.Roles.MODERATOR;
            }
            else {
                newUserRole = User.Roles.USER;
            }
        }
        return user;
    }

    @Inject
    public UserEdit(UserService service){
        this.service = service;
    }

    private List<User> availableUsers;

    public Collection<User> getAvailableUsers() {
        if(availableUsers == null) {
            availableUsers = service.findAllUsers();
        }
        return availableUsers;
    }

    public String saveUser(){
        // call service to change user roles
        if(newUserRole.equals(User.Roles.ADMIN)) {
            user.setRoles(List.of(User.Roles.USER, User.Roles.MODERATOR, User.Roles.ADMIN));
        }
        else if(newUserRole.equals(User.Roles.MODERATOR)) {
            user.setRoles(List.of(User.Roles.USER, User.Roles.MODERATOR));
        }
        else {
            user.setRoles(List.of(User.Roles.USER));
        }
        service.saveUser(user);
        return "user_list?faces-redirect=true";
    }
}
