package com.klopsi.exercise.view;

import com.klopsi.exercise.ExerciseService;
import com.klopsi.exercise.model.Exercise;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ExerciseCreate extends ExerciseEdit{

	@Inject
	public ExerciseCreate(ExerciseService service){
		super(service);
	}

	@PostConstruct
	public void init(){
		setExercise(new Exercise());
	}
}
