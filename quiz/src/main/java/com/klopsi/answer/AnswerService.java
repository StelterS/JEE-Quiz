package com.klopsi.answer;

import com.klopsi.answer.model.Answer;
import com.klopsi.exercise.ExerciseService;
import com.klopsi.exercise.model.Exercise;
import com.klopsi.user.UserService;
import com.klopsi.user.model.User;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@NoArgsConstructor
public class AnswerService {
	@PersistenceContext
	private EntityManager em;

	public List<Answer> findAllAnswers() {
		return em.createNamedQuery(Answer.Queries.FIND_ALL, Answer.class).getResultList();
	}

	public synchronized Answer findAnswer(int id) {
		return em.find(Answer.class, id);
	}

	@Transactional
	public void removeAnswer(Answer answer) {
		// remove from corresponding exercise
//		exerciseService.deleteAnsFromCorrespondingExercises(answer);
//		userService.deleteAnsFromCorrespondingUser(answer);
//		// remove answer from service aka "database"
//		answers.removeIf(a -> a.equals(answer));
		em.remove(em.merge(answer));
	}

	@Transactional
	public synchronized void saveAnswer(Answer answer){
//		if(answer.getId() != 0) {
//			// remove from corresponding exercise
//			exerciseService.deleteAnsFromCorrespondingExercises(answer);
//			userService.deleteAnsFromCorrespondingUser(answer);
//			// change element
//			answers.removeIf(a -> a.getId() == answer.getId());
//			answers.add(new Answer(answer));
//		}
//		else {
//			answer.setId(answers.stream().mapToInt(Answer::getId).max().orElse(0) + 1);
//			answers.add(new Answer(answer));
//		}
//		// add answer to corresponding exercise
//		exerciseService.addAnswerToExercise(answer);
//		userService.addAnswerToUser(answer);
		if (answer.getId() == null) {
			em.persist(answer);
		} else {
			em.merge(answer);
		}
	}

	// does not delete answer from corresponding exercise (one way deletion)
//	public void deleteAnswerFromExercise(Exercise exercise) {
//		List<Answer> exerciseAnswers = exercise.getAnswers().stream().map(Answer::new).collect(Collectors.toList());
//		for (Answer answer : exerciseAnswers) {
//			userService.deleteAnsFromCorrespondingUser(answer);
//		}
//		// delete those answers
//		answers.removeIf(answer -> exercise.getAnswers().stream().anyMatch(element -> element.getId() == answer.getId()));
//	}
//
//	public void deleteUserAnswers(User user) {
//		List<Answer> userAnswers = user.getAnswers().stream().map(Answer::new).collect(Collectors.toList());
//		for (Answer answer : userAnswers) {
//			exerciseService.deleteAnsFromCorrespondingExercises(answer);
//		}
//		// delete all those answers as well
//		answers.removeIf(answer -> user.getAnswers().stream().anyMatch(element -> element.getId() == answer.getId()));
//	}
//
//	public void addExerciseToAnswer(Exercise exercise) {
//		for (Answer answer : answers) {
//			if (answer.getExercise().getId() == exercise.getId()) {
//				answer.setExercise(exercise);
//			}
//		}
//	}
//
//	public void addUserToAnswer(User user) {
//		for(Answer answer : answers) {
//			if (answer.getUser().getId() == user.getId()) {
//				answer.setUser(user);
//			}
//		}
//	}
//
//	public void deleteAnswerFromExerciseList(Answer answer) {
//		exerciseService.deleteAnsFromCorrespondingExercises(answer);
//	}
//
//	public void deleteAnswerFromUserList(Answer answer) {
//		userService.deleteAnsFromCorrespondingUser(answer);
//	}

}
