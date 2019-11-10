package com.klopsi.user.view;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Context for ongoing http session.
 */
@SessionScoped
@Named
public class UserContext implements Serializable {

    @Inject
    private ExternalContext context;

    public String logout() {
        context.invalidateSession();
        return "/index?faces-redirect=true";
    }

}

