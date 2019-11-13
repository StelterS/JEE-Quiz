package com.klopsi.answer.view;

import com.klopsi.answer.AnswerService;
import com.klopsi.answer.model.Answer;
import com.klopsi.user.UserService;
import com.klopsi.user.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Named
@ViewScoped
public class AnswerEdit implements Serializable {
	@Inject
	private HttpServletRequest securityContext;

	private AnswerService service;
	private UserService userService;

	@Setter
	private Answer answer;

	public Answer getAnswer() {
		if(answer == null) {
			answer = new Answer();
		}
		return answer;
	}

	@Getter
	@Setter
	private Answer lastAnswer;

	@Inject
	public AnswerEdit(AnswerService service, UserService userService){
		this.service = service;
		this.userService = userService;
	}

	public String saveAnswer() {
		User user = userService.findUserByLogin(securityContext.getUserPrincipal().getName());
		answer.setUser(user);
		service.saveAnswer(answer);
		return "answer_list?faces-redirect=true";
	}
}
