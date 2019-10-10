package com.klopsi.exercise;

import com.klopsi.exercise.model.Answer;
import com.klopsi.exercise.model.Difficulty;
import com.klopsi.exercise.model.Exercise;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor
public class ExerciseService {
	private final List<Exercise> exercises = new ArrayList<>();
	private final List<Answer> answers = new ArrayList<>();

	@PostConstruct
	public void init(){
		exercises.add(new Exercise(1, "Chrzest Polski", "Kiedy odbył się chrzest Polski?", Difficulty.EASY, 10, List.of()));
		exercises.add(new Exercise(2, "Polski Maluch", "Kiedy rozpoczęła się produkcja Malucha?", Difficulty.MEDIUM, 25, List.of()));
		answers.add(new Answer(1, "1972", 100, LocalDate.of(2019, 10, 10), exercises.get(1)));
		answers.add(new Answer(2, "996", 100, LocalDate.of(2017, 9, 10), exercises.get(0)));
		answers.add(new Answer(3, "1968", 75, LocalDate.of(2016, 10, 21), exercises.get(1)));
		exercises.get(0).setAnswers(List.of(answers.get(1)));
		exercises.get(1).setAnswers(List.of(answers.get(0), answers.get(2)));
	}

	public synchronized List<Answer> findAllAnswers() {
		return answers.stream().map(Answer::new).collect(Collectors.toList());
	}

	public synchronized Answer findAnswer(int id) {
		return answers.stream().filter(answer -> answer.getId() == id).findFirst().map(Answer::new).orElse(null);
	}

	public synchronized List<Exercise> findAllExercises() {
		return exercises.stream().map(Exercise::new).collect(Collectors.toList());
	}

	public synchronized Exercise findExercise(int id) {
		return exercises.stream().filter(exercise -> exercise.getId() == id).findFirst().map(Exercise::new).orElse(null);
	}

	public void removeAnswer(Answer answer) {
		// remove from corresponding exercise
		List<Answer> newAnswers = exercises.get(answer.getExercise().getId() - 1).getAnswers().stream().map(Answer::new).collect(Collectors.toList());
		newAnswers.removeIf(a -> a.getId() == answer.getId());
		exercises.get(answer.getExercise().getId() - 1).setAnswers(newAnswers);
		// remove answer from service aka "database"
		answers.removeIf(a -> a.equals(answer));
	}

	public void removeExercise(Exercise exercise) {
		// remove all corresponding answers
		answers.removeIf(answer -> exercise.getAnswers().stream().anyMatch(element -> element.getId() == answer.getId()));
		// remove exercise itself
		exercises.removeIf(e -> e.equals(exercise));
	}

	public synchronized void saveExercise(Exercise exercise){
		// initialize answers to empty list on addition
		if(exercise.getAnswers() == null){
			exercise.setAnswers(List.of());
		}

		if(exercise.getId() != 0) {
			exercises.removeIf(e -> e.getId() == exercise.getId());
			exercises.add(new Exercise(exercise));
		}
		else {
			exercise.setId(exercises.stream().mapToInt(Exercise::getId).max().orElse(0) + 1);
			exercises.add(new Exercise(exercise));
		}
	}

	public synchronized void saveAnswer(Answer answer){
		if(answer.getId() != 0) {
			answers.removeIf(a -> a.getId() == answer.getId());
			answers.add(new Answer(answer));
		}
		else {
			answer.setId(answers.stream().mapToInt(Answer::getId).max().orElse(0) + 1);
			answers.add(new Answer(answer));
		}
		// add answer to corresponding exercise
		List<Answer> newAnswers = exercises.get(answer.getExercise().getId() - 1).getAnswers().stream().map(Answer::new).collect(Collectors.toList());
		newAnswers.add(answer);
		exercises.get(answer.getExercise().getId() - 1).setAnswers(newAnswers);
	}

}
