package com.klopsi.user.view;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Context for ongoing http session.
 */
@SessionScoped
@Named
public class UserContext implements Serializable {

    @Inject
    private HttpServletRequest context;

    public String logout() throws ServletException {
        context.logout();
        return "/index?faces-redirect=true";
    }
}
