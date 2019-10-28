package com.klopsi.user.view.converter;

import com.klopsi.user.UserService;
import com.klopsi.user.model.User;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = User.class, managed = true)
@Dependent
public class UserConverter implements Converter<User> {
    private UserService service;

    @Inject
    public UserConverter(UserService service){
        this.service = service;
    }

    @Override
    public User getAsObject(FacesContext context, UIComponent component, String value){
        if (value == null || value.isEmpty()) {
            return null;
        }
        return service.findUser(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, User value) {
        if (value.getId() == null) {
            return "";
        }
        return Integer.toString(value.getId());
    }
}
