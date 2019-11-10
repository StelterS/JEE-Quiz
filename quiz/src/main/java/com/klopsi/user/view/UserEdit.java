package com.klopsi.user.view;

import com.klopsi.user.UserService;
import com.klopsi.user.model.User;
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

    @Setter
    private User user;

    public User getUser() {
        if(user == null) {
            user = new User();
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
        service.saveUser(user);
        return "user_list?faces-redirect=true";
    }
}
