package com.klopsi.exercise.view;

import com.klopsi.exercise.ExerciseService;
import com.klopsi.exercise.model.Answer;
import com.klopsi.exercise.model.Exercise;
import lombok.Getter;
import lombok.Setter;

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
		service.saveAnswer(answer);
		return "answer_list?faces-redirect=true";
	}
}
