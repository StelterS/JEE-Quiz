package com.klopsi.exercise;

import com.klopsi.answer.AnswerService;
import com.klopsi.answer.model.Answer;
import com.klopsi.exercise.model.Difficulty;
import com.klopsi.exercise.model.Exercise;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor
public class ExerciseService {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void init() {
		Exercise exercise1 = new Exercise("Chrzest Polski", "Kiedy odbył się chrzest Polski?", Difficulty.EASY, 10);
		Exercise exercise2 = new Exercise("Polski Maluch", "Kiedy rozpoczęła się produkcja Malucha?", Difficulty.MEDIUM, 25);
		Exercise exercise3 = new Exercise("Kosmos", "W którym roku Neil Armstrong stanął na Księżycu?", Difficulty.EASY, 10);
		Exercise exercise4 = new Exercise("Ewolucja", "Podaj nazwisko osoby, która wymyśliła teorię ewolucji", Difficulty.MEDIUM, 15);
		Exercise exercise5 = new Exercise("Podbój kosmosu", "Kto jako pierwszy poleciał w kosmos?", Difficulty.HARD, 25);
		Exercise exercise6 = new Exercise("Ziemia", "Czy Ziemia jest największą planetą układu słonecznego?", Difficulty.EASY, 5);
		Exercise exercise7 = new Exercise("Dinozaury", "Podaj nazwę najstraszniejszego dinozaura", Difficulty.MEDIUM, 25);
		Exercise exercise8 = new Exercise("Ananasy", "Czy ananasy rosną na drzewach?", Difficulty.MEDIUM, 25);

		em.persist(exercise1);
		em.persist(exercise2);
		em.persist(exercise3);
		em.persist(exercise4);
		em.persist(exercise5);
		em.persist(exercise6);
		em.persist(exercise7);
		em.persist(exercise8);
	}

	public List<Exercise> findAllExercises() {
//		return exercises.stream().map(Exercise::new).collect(Collectors.toList());
		return em.createNamedQuery(Exercise.Queries.FIND_ALL, Exercise.class).getResultList();
	}

	public List<Exercise> findAllExercises(int offset, int limit) {
		return em.createNamedQuery(Exercise.Queries.FIND_ALL, Exercise.class)
			.setFirstResult(offset)
			.setMaxResults(limit)
			.getResultList();
	}

	public long countExercises() {
		return em.createNamedQuery(Exercise.Queries.COUNT, Long.class).getSingleResult();
	}

	public Exercise findExercise(int id) {
		return em.find(Exercise.class, id);
	}

	@Transactional
	public void removeExercise(Exercise exercise) {
		// remove all corresponding answers
//		answerService.deleteAnswerFromExercise(exercise);
//		// remove exercise itself
//		exercises.removeIf(e -> e.equals(exercise));
		em.remove(em.merge(exercise));
	}

	@Transactional
	public synchronized void saveExercise(Exercise exercise){
		// initialize answers to empty list on addition
//		if(exercise.getAnswers() == null){
//			exercise.setAnswers(List.of());
//		}
//
//		if(exercise.getId() != 0) {
//			// copy answers on edit
//			//List<Answer> answers = exercises.get(exercise.getId() - 1).getAnswers().stream().map(Answer::new).collect(Collectors.toList());
//			//exercise.setAnswers(answers);
//			// update exercise
//			exercises.removeIf(e -> e.getId() == exercise.getId());
//			exercises.add(new Exercise(exercise));
//			answerService.addExerciseToAnswer(exercise);
//		}
//		else {
//			exercise.setId(exercises.stream().mapToInt(Exercise::getId).max().orElse(0) + 1);
//			exercises.add(new Exercise(exercise));
//		}
		if (exercise.getId() == null) {
			em.persist(exercise);
		} else {
			em.merge(exercise);
		}
	}

//	public void addAnswerToExercise(Answer answer) {
//		for(Exercise exercise : exercises) {
//			if (exercise.getId() == answer.getExercise().getId()) {
//				List<Answer> newAnswers = exercise.getAnswers().stream().map(Answer::new).collect(Collectors.toList());
//				newAnswers.add(answer);
//				exercise.setAnswers(newAnswers);
//			}
//		}
//	}
//
//	public void deleteAnsFromCorrespondingExercises(Answer answer) {
//		for (Exercise exercise : exercises) {
//			if (exercise.getId() == answer.getExercise().getId()) {
//				List<Answer> newAnswers = exercise.getAnswers().stream().map(Answer::new).collect(Collectors.toList());
//				newAnswers.removeIf(a -> a.getId() == answer.getId());
//				exercise.setAnswers(newAnswers);
//			}
//		}
//	}

}
