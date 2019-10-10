package com.klopsi.exercise.view;

import com.klopsi.exercise.ExerciseService;
import com.klopsi.exercise.model.Difficulty;
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
public class ExerciseEdit implements Serializable {
	private ExerciseService service;

	@Getter
	@Setter
	private Exercise exercise;

	@Inject
	public ExerciseEdit(ExerciseService service){
		this.service = service;
	}

	public Collection<Difficulty> getAvailableDifficulties(){
		return List.of(Difficulty.values());
	}

	public String saveExercise(){
		service.saveExercise(exercise);
		return "exercise_list?faces-redirect=true";
	}

}
