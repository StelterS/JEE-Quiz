package com.klopsi.exercise.view;

import com.klopsi.exercise.ExerciseService;
import com.klopsi.exercise.model.Difficulty;
import com.klopsi.exercise.model.Exercise;
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

	@Setter
	private Exercise exercise;

	public Exercise getExercise() {
		if(exercise == null) {
			exercise = new Exercise();
		}
		return exercise;
	}

	@Inject
	public ExerciseEdit(ExerciseService service){
		this.service = service;
	}

	public Collection<Difficulty> getAvailableDifficulties(){
		return List.of(Difficulty.values());
	}

	private List<Exercise> availableExercises;

	public Collection<Exercise> getAvailableExercises() {
		if(availableExercises == null) {
			availableExercises = service.findAllExercises();
		}
		return availableExercises;
	}

	public String saveExercise(){
		service.saveExercise(exercise);
		return "exercise_list?faces-redirect=true";
	}
}
