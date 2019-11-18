package com.klopsi.exercise;

import com.klopsi.exercise.interceptor.CheckExerciseUser;
import com.klopsi.exercise.model.Difficulty;
import com.klopsi.exercise.model.Exercise;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.security.AccessControlException;
import java.util.List;

@ApplicationScoped
@NoArgsConstructor
public class ExerciseService {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private HttpServletRequest securityContext;

	@CheckExerciseUser
	public List<Exercise> findAllExercises() {
		return em.createNamedQuery(Exercise.Queries.FIND_ALL, Exercise.class).getResultList();
	}

	@CheckExerciseUser
	public List<Exercise> findAllExercises(int offset, int limit) {
		return em.createNamedQuery(Exercise.Queries.FIND_ALL, Exercise.class)
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();
	}

	@CheckExerciseUser
	public long countExercises() {
		return em.createNamedQuery(Exercise.Queries.COUNT, Long.class).getSingleResult();
	}

	@CheckExerciseUser
	public Exercise findExercise(int id) {
		return em.find(Exercise.class, id);
	}

	@CheckExerciseUser
	public List<Exercise> findExerciseByDifficulty(List<Difficulty> difficulties) {
		return em.createNamedQuery(Exercise.Queries.FIND_BY_DIFFICULTY, Exercise.class)
				.setParameter("difficulties", difficulties)
				.getResultList();
	}

	@CheckExerciseUser
	@Transactional
	public void removeExercise(Exercise exercise) {
		em.remove(em.merge(exercise));
	}

	@CheckExerciseUser
	@Transactional
	public synchronized void saveExercise(Exercise exercise){
		if (exercise.getId() == null) {
			em.persist(exercise);
		} else {
			em.merge(exercise);
		}
	}
}
