package com.klopsi.exercise.view.converter;

import com.klopsi.exercise.ExerciseService;
import com.klopsi.exercise.model.Exercise;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = Exercise.class, managed = true)
@Dependent
public class ExerciseConverter implements Converter<Exercise> {
	private ExerciseService service;

	@Inject
	public ExerciseConverter(ExerciseService service){
		this.service = service;
	}

	@Override
	public Exercise getAsObject(FacesContext context, UIComponent component, String value){
		if (value == null || value.isEmpty()) {
			return null;
		}
		return service.findExercise(Integer.parseInt(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Exercise value) {
		if (value.getId() == null) {
			return "";
		}
		return Integer.toString(value.getId());
	}
}
