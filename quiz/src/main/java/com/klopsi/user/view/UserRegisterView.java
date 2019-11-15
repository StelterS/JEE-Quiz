package com.klopsi.user.view;

import com.klopsi.user.UserService;
import com.klopsi.user.model.User;
import com.klopsi.user.view.model.RegistrationForm;
import lombok.Getter;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashSet;

import static com.klopsi.user.HashUtils.sha256;

@Named
@ViewScoped
public class UserRegisterView implements Serializable {
	private UserService service;

	@Inject
	public UserRegisterView(UserService service){
		this.service = service;
	}

	@Getter
	private RegistrationForm form = new RegistrationForm();

	public String register() {
		// register user and redirect
		User newUser = User.builder()
			.login(form.getLogin())
			.password(sha256(form.getPassword()))
			.firstName(form.getFirstName())
			.lastName(form.getLastName())
			.birthDate(form.getBirthDate())
			.answers(new HashSet<>())
			.role(User.Roles.USER)
			.build();

		service.saveUser(newUser);

		return "user_registered?faces-redirect=true";
	}
}
