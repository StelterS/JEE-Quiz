package com.klopsi.exercise.view;

import com.klopsi.exercise.ExerciseService;
import com.klopsi.exercise.model.Difficulty;
import com.klopsi.exercise.model.Exercise;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ExerciseList {
	private ExerciseService service;
	private List<Exercise> exercises;

	@Getter
	@Setter
	private Difficulty difficulty;

	@Inject
	public ExerciseList(ExerciseService service){
		this.service = service;
	}

	public List<Exercise> getExercises() {
		if(exercises == null){
			if (difficulty == null) {
				exercises = service.findAllExercises();
			}
			else {
				exercises = service.findExerciseByDifficulty(List.of(difficulty));
			}
		}
		return exercises;
	}

	public String removeExercise(Exercise exercise) {
		service.removeExercise(exercise);
		return "/quiz-views/exercise_list?faces-redirect=true";
	}

	public String filter() {
		if (difficulty != null) {
			return "/quiz-views/exercise_list?faces-redirect=true" + "difficulty=" + difficulty;
		}
		else {
			return "/quiz-views/exercise_list?faces-redirect=true";
		}
	}
}
