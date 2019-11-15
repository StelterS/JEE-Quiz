package com.klopsi.user.view;

import com.klopsi.user.UserService;
import com.klopsi.user.view.model.ChangePassForm;
import com.klopsi.user.model.User;
import lombok.Getter;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

import static com.klopsi.user.HashUtils.sha256;


@ViewScoped
@Named
public class UserChangePass implements Serializable {
	private UserService service;

	@Getter
	private ChangePassForm form = new ChangePassForm();

	@Inject
	private HttpServletRequest context;

	@Inject
	public UserChangePass(UserService service){
		this.service = service;
	}

	public void changePassword() {
		User currentUser = service.findUserByLogin(context.getUserPrincipal().getName());
		currentUser.setPassword(sha256(form.getPassword()));
		service.saveUser(currentUser);
		try {
			context.logout();
		}
		catch (ServletException exception) {
			exception.printStackTrace();
		}
	}
}
