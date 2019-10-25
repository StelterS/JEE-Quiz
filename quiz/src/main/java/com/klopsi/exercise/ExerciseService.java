package com.klopsi.exercise;

import com.klopsi.answer.AnswerService;
import com.klopsi.answer.model.Answer;
import com.klopsi.exercise.model.Difficulty;
import com.klopsi.exercise.model.Exercise;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor
public class ExerciseService {
	private final List<Exercise> exercises = new ArrayList<>();
	private AnswerService answerService;

	@Inject
	public ExerciseService(AnswerService answerService){
		this.answerService = answerService;
	}

	@PostConstruct
	public void init(){
		exercises.add(new Exercise(1, "Chrzest Polski", "Kiedy odbył się chrzest Polski?", Difficulty.EASY, 10, List.of()));
		exercises.add(new Exercise(2, "Polski Maluch", "Kiedy rozpoczęła się produkcja Malucha?", Difficulty.MEDIUM, 25, List.of()));
		exercises.add(new Exercise(3, "Kosmos", "W którym roku Neil Armstrong stanął na Księżycu?", Difficulty.EASY, 10, List.of()));
		exercises.add(new Exercise(4, "Ewolucja", "Podaj nazwisko osoby, która wymyśliła teorię ewolucji", Difficulty.MEDIUM, 15, List.of()));
		exercises.add(new Exercise(5, "Podbój kosmosu", "Kto jako pierwszy poleciał w kosmos?", Difficulty.HARD, 25, List.of()));
		exercises.add(new Exercise(6, "Ziemia", "Czy Ziemia jest największą planetą układu słonecznego?", Difficulty.EASY, 5, List.of()));
		exercises.add(new Exercise(7, "Dinozaury", "Podaj nazwę najstraszniejszego dinozaura", Difficulty.MEDIUM, 25, List.of()));
		exercises.add(new Exercise(8, "Ananasy", "Czy ananasy rosną na drzewach?", Difficulty.MEDIUM, 25, List.of()));
	}

	public synchronized List<Exercise> findAllExercises() {
		return exercises.stream().map(Exercise::new).collect(Collectors.toList());
	}

	public synchronized List<Exercise> findAllExercises(int offset, int limit) {
		return exercises.stream().skip(offset).limit(limit).map(Exercise::new).collect(Collectors.toList());
	}

	public synchronized int countExercises() {
		return exercises.size();
	}

	public synchronized Exercise findExercise(int id) {
		return exercises.stream().filter(exercise -> exercise.getId() == id).findFirst().map(Exercise::new).orElse(null);
	}

	public void removeExercise(Exercise exercise) {
		// remove all corresponding answers
		answerService.deleteAnswerFromExercise(exercise);
		// remove exercise itself
		exercises.removeIf(e -> e.equals(exercise));
	}

	public synchronized void saveExercise(Exercise exercise){
		// initialize answers to empty list on addition
		if(exercise.getAnswers() == null){
			exercise.setAnswers(List.of());
		}

		if(exercise.getId() != 0) {
			// copy answers on edit
			//List<Answer> answers = exercises.get(exercise.getId() - 1).getAnswers().stream().map(Answer::new).collect(Collectors.toList());
			//exercise.setAnswers(answers);
			// update exercise
			exercises.removeIf(e -> e.getId() == exercise.getId());
			exercises.add(new Exercise(exercise));
			answerService.addExerciseToAnswer(exercise);
		}
		else {
			exercise.setId(exercises.stream().mapToInt(Exercise::getId).max().orElse(0) + 1);
			exercises.add(new Exercise(exercise));
		}
	}

	public void addAnswerToExercise(Answer answer) {
		for(Exercise exercise : exercises) {
			if (exercise.getId() == answer.getExercise().getId()) {
				List<Answer> newAnswers = exercise.getAnswers().stream().map(Answer::new).collect(Collectors.toList());
				newAnswers.add(answer);
				exercise.setAnswers(newAnswers);
			}
		}
	}

	public void deleteAnsFromCorrespondingExercises(Answer answer) {
		for (Exercise exercise : exercises) {
			if (exercise.getId() == answer.getExercise().getId()) {
				List<Answer> newAnswers = exercise.getAnswers().stream().map(Answer::new).collect(Collectors.toList());
				newAnswers.removeIf(a -> a.getId() == answer.getId());
				exercise.setAnswers(newAnswers);
			}
		}
	}

}
