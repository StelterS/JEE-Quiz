package com.klopsi.answer.view;

import com.klopsi.answer.AnswerService;
import com.klopsi.answer.model.Answer;
import lombok.Getter;
import lombok.Setter;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class AnswerEdit implements Serializable {
	private AnswerService service;

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
	public AnswerEdit(AnswerService service){
		this.service = service;
	}

	public String saveAnswer() {
		service.saveAnswer(answer);
		return "answer_list?faces-redirect=true";
	}
}
