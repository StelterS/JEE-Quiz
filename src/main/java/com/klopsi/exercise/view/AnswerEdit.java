package com.klopsi.exercise.view;

import com.klopsi.exercise.ExerciseService;
import com.klopsi.exercise.model.Answer;
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
	private ExerciseService service;
	private List<Exercise> availableExercises;

	@Getter
	@Setter
	private Answer answer;

	@Getter
	@Setter
	private Answer lastAnswer;

	@Inject
	public AnswerEdit(ExerciseService service){
		this.service = service;
	}

	public Collection<Exercise> getAvailableExercises() {
		if(availableExercises == null) {
			availableExercises = service.findAllExercises();
		}
		return availableExercises;
	}

	public String saveAnswer() {
		if (lastAnswer.getId() != 0){
			service.deleteAnsFromCorrespondingExercises(lastAnswer);
		}
		service.saveAnswer(answer);
		return "answer_list?faces-redirect=true";
	}

	public void saveLastExercise() {
		setLastAnswer(new Answer(answer));
	}

	@PostConstruct
	public void init() {
		setAnswer(new Answer());
	}
}
