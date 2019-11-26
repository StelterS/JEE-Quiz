package com.klopsi.answer.view;

import com.klopsi.answer.AnswerService;
import com.klopsi.answer.model.Answer;
import com.klopsi.user.UserService;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class AnswerList {
	private AnswerService service;
	private UserService userService;
	private List<Answer> answers;

	@Getter
	private LazyAnswerModel lazyModel;

	@PostConstruct
	private void init() {
		lazyModel = new LazyAnswerModel(service, userService);
	}

	@Inject
	public AnswerList(AnswerService service, UserService userService){
		this.service = service;
		this.userService = userService;
	}

	/*public List<Answer> getAnswers() {
		if(answers == null){
			answers = service.findAllAnswers();
		}
		return answers;
	}*/

	public String removeAnswer(Answer answer) {
		service.removeAnswer(answer);
		return "answer_list?faces-redirect=true";
	}
}
