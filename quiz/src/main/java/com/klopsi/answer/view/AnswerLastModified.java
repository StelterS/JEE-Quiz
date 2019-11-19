package com.klopsi.answer.view;

import com.klopsi.answer.AnswerService;
import com.klopsi.answer.model.Answer;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class AnswerLastModified {
	private AnswerService service;
	private List<Answer> answers;

	@Inject
	public AnswerLastModified(AnswerService service){
		this.service = service;
	}

	public List<Answer> getAnswers() {
		if(answers == null){
			answers = service.findAllAnswersByModificationDate();
		}
		return answers;
	}
}
