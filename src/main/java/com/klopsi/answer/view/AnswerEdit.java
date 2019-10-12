package com.klopsi.answer.view;

import com.klopsi.answer.AnswerService;
import com.klopsi.answer.model.Answer;
import com.klopsi.exercise.ExerciseService;
import com.klopsi.exercise.model.Exercise;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Named
@ViewScoped
public class AnswerEdit implements Serializable {
	private AnswerService service;

	@Getter
	@Setter
	private Answer answer;

	@Getter
	@Setter
	private Answer lastAnswer;

	@Getter
	@Setter
	private Integer answerId;

	@Inject
	public AnswerEdit(AnswerService service){
		this.service = service;
	}

	public String saveAnswer() {
		if (lastAnswer.getId() != 0){
			service.deleteAnswerFromExerciseList(lastAnswer);
		}
		service.saveAnswer(answer);
		return "answer_list?faces-redirect=true";
	}

	public void initialize() {
		if(answerId == null) {
			setAnswer(new Answer());
		}
		else {
			setAnswer(service.findAnswer(answerId));
		}
		setLastAnswer(new Answer(answer));
	}
}
