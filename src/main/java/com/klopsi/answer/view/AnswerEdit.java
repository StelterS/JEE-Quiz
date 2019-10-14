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

	@Setter
	private Answer answer;

	public Answer getAnswer() {
		if(answer == null) {
			answer = new Answer();
		}
		setLastAnswer(new Answer(answer));
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
		if (lastAnswer.getId() != 0){
			service.deleteAnswerFromExerciseList(lastAnswer);
		}
		service.saveAnswer(answer);
		return "answer_list?faces-redirect=true";
	}

}
