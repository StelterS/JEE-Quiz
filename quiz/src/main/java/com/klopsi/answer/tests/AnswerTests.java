package com.klopsi.answer.tests;

import com.klopsi.answer.AnswerService;
import com.klopsi.answer.model.Answer;
import com.klopsi.exercise.ExerciseService;
import com.klopsi.exercise.model.Exercise;
import com.klopsi.user.UserService;
import com.klopsi.user.model.User;
import org.primefaces.model.SortOrder;
import com.klopsi.answer.model.Answer_;
import javax.persistence.metamodel.Attribute;
import java.util.*;
import java.util.stream.Collectors;

public class AnswerTests {
	private AnswerService answerService;
	private UserService userService;
	private ExerciseService exerciseService;

	public AnswerTests(AnswerService answerService, UserService userService, ExerciseService exerciseService)
	{
		this.answerService = answerService;
		this.userService = userService;
		this.exerciseService = exerciseService;
	}
    // np. dodaj do bazki jakieś odp. i sprawdz ile ich jest
    // possortuj przez criteria i posortuj przez metody javki
    // filtruj tak samo jw
    // usun element i spr czy sie zmniejszyla sie ilosc
    // update elementu i sprawdz czy zostal zmieniony
	public boolean createReadAnswerTest()
	{
		List<User> users = userService.findAllUsers();
		List<Exercise> exercises = exerciseService.findAllExercises();

		if(users.isEmpty() || exercises.isEmpty())
		{
			return false;
		}

		Answer answer1 = Answer.builder()
			.content("Test content")
			.percent(0)
			.exercise(exercises.get(0))
			.user(users.get(0))
			.build();

		Answer answer2 = Answer.builder()
			.content("Test content1")
			.percent(50)
			.exercise(exercises.get(0))
			.user(users.get(0))
			.build();

		Answer answer3 = Answer.builder()
			.content("Test content2")
			.percent(100)
			.exercise(exercises.get(0))
			.user(users.get(0))
			.build();

		answerService.saveAnswer(answer1);
		answerService.saveAnswer(answer2);
		answerService.saveAnswer(answer3);

		List<Answer> answers = answerService.findAllAnswers();

		return answers.size() > 2;
	}

	public boolean updateAnswerTest()
	{
		List<Answer> answers = answerService.findAllAnswers();
		for (Answer ans: answers) {
			ans.setContent("Content Changed");
			answerService.saveAnswer(ans);
		}

		List<Answer> updatedAnswers = answerService.findAllAnswers();
		for(Answer ans: updatedAnswers){
			if(!ans.getContent().equals("Content Changed")){
				return false;
			}
		}
		return true;
	}

	public boolean deleteAnswerTest()
	{
		List<Answer> answers = answerService.findAllAnswers();
		int size = answers.size();
		if(size <= 0) {
			return false;
		}
		answerService.removeAnswer(answers.get(0));
		List<Answer> newAnswers = answerService.findAllAnswers();
		int newSize = newAnswers.size();
		return newSize == (size - 1);
	}

	public boolean sortByScoreTest()
	{
		// sort by score
		List<Answer> answers = answerService.findAllAnswers(0, 5, new HashMap<>(), "percent", SortOrder.ASCENDING);

		List<Answer> unorderedAnswers = answerService.findAllAnswers();
		unorderedAnswers.sort(new Comparator<Answer>() {
			@Override
			public int compare(Answer answer1, Answer answer2) {
				return answer1.getPercent().compareTo(answer2.getPercent());
			}
		});

		if (!answers.equals(unorderedAnswers)) {
			return false;
		}

		answers = answerService.findAllAnswers(0, 5, new HashMap<>(), "percent", SortOrder.DESCENDING);
		unorderedAnswers = answerService.findAllAnswers();
		unorderedAnswers.sort(new Comparator<Answer>() {
			@Override
			public int compare(Answer answer1, Answer answer2) {
				return answer2.getPercent().compareTo(answer1.getPercent());
			}
		});

		return answers.equals(unorderedAnswers);
	}

	public boolean filterByScore() {
		Map<Attribute<Answer, ?>, Object> jpaFilters = new HashMap<>();
		jpaFilters.put(Answer_.percent, 50);
		List<Answer> answers = answerService.findAllAnswers(0, 5, jpaFilters, null, null);

		List<Answer> allAnswers = answerService.findAllAnswers();
		List<Answer> filteredAnswers = allAnswers.stream().filter(a -> a.getPercent().equals(50)).collect(Collectors.toList());

		return answers.equals(filteredAnswers);
	}
}
