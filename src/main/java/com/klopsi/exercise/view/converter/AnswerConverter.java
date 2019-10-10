package com.klopsi.exercise.view.converter;

import com.klopsi.exercise.ExerciseService;
import com.klopsi.exercise.model.Answer;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = Answer.class, managed = true)
@Dependent
public class AnswerConverter implements Converter<Answer> {
	private ExerciseService service;

	@Inject
	public AnswerConverter(ExerciseService service){
		this.service = service;
	}

	@Override
	public Answer getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		if(Integer.parseInt(value) == 0) {
			return new Answer();
		}
		return service.findAnswer(Integer.parseInt(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Answer value) {
		if (value == null) {
			return "";
		}
		return Integer.toString(value.getId());
	}

}
