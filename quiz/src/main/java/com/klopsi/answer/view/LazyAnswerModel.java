package com.klopsi.answer.view;

import com.klopsi.answer.AnswerService;
import com.klopsi.answer.model.Answer;
import com.klopsi.answer.model.Answer_;
import com.klopsi.user.UserService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.persistence.metamodel.Attribute;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LazyAnswerModel extends LazyDataModel<Answer> {
	private AnswerService service;
	private UserService userService;

	public LazyAnswerModel(AnswerService service, UserService userService) {this.service = service; this.userService = userService;}

	@Override
	public List<Answer> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters)
	{
		Map<Attribute<Answer, ?>, Object> jpaFilters = new HashMap<>();
		if (filters.containsKey(Answer_.CONTENT)) {
			jpaFilters.put(Answer_.content, filters.get(Answer_.CONTENT));
		}
		if (filters.containsKey(Answer_.PERCENT)) {
			jpaFilters.put(Answer_.percent, filters.get(Answer_.PERCENT));
		}
		if (filters.containsKey(Answer_.USER)) {
			jpaFilters.put(Answer_.user, filters.get(Answer_.USER));
		}
		if (filters.containsKey(Answer_.EXERCISE)) {
			jpaFilters.put(Answer_.exercise, filters.get(Answer_.EXERCISE));
		}
		if (filters.containsKey(Answer_.LAST_MODIFICATION_DATE)) {
			jpaFilters.put(Answer_.lastModificationDate, filters.get(Answer_.LAST_MODIFICATION_DATE));
		}
		List<Answer> answers = service.findAllAnswers(first, pageSize, jpaFilters, sortField, sortOrder);
		setRowCount((int) service.countAnswers(jpaFilters));
		return answers;
	}
}
