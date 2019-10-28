package com.klopsi.exercise.view;

import com.klopsi.exercise.ExerciseService;
import com.klopsi.exercise.model.Exercise;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ExerciseList {
	private ExerciseService service;
	private List<Exercise> exercises;

	@Inject
	public ExerciseList(ExerciseService service){
		this.service = service;
	}

	public List<Exercise> getExercises() {
		if(exercises == null){
			exercises = service.findAllExercises();
		}
		return exercises;
	}

	public String removeExercise(Exercise exercise) {
		service.removeExercise(exercise);
		return "exercise_list?faces-redirect=true";
	}

	public String init() {
		service.init();
		return "exercise_list?faces-redirect=true";
	}
}
