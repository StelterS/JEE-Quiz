package com.klopsi.answer.view.converter;

import com.klopsi.answer.AnswerService;
import com.klopsi.answer.model.Answer;
import com.klopsi.exercise.ExerciseService;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = Answer.class, managed = true)
@Dependent
public class AnswerConverter implements Converter<Answer> {
	private AnswerService service;

	@Inject
	public AnswerConverter(AnswerService service){
		this.service = service;
	}

	@Override
	public Answer getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty()) {
			return null;
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
