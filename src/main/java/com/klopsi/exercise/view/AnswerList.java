package com.klopsi.exercise.view;

import com.klopsi.exercise.ExerciseService;
import com.klopsi.exercise.model.Answer;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class AnswerList {
	private ExerciseService service;
	private List<Answer> answers;

	@Inject
	public AnswerList(ExerciseService service){
		this.service = service;
	}

	public List<Answer> getAnswers() {
		if(answers == null){
			answers = service.findAllAnswers();
		}
		return answers;
	}

	public String removeAnswer(Answer answer) {
		service.removeAnswer(answer);
		return "answer_list?faces-redirect=true";
	}
}
