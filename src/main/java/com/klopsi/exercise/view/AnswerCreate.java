package com.klopsi.exercise.view;

import com.klopsi.exercise.ExerciseService;
import com.klopsi.exercise.model.Answer;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class AnswerCreate extends AnswerEdit {

	@Inject
	public AnswerCreate(ExerciseService service){
		super(service);
	}

	@PostConstruct
	public void init() {
		setAnswer(new Answer());
	}
}
