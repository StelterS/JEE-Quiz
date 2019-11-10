package com.klopsi.exercise;

import com.klopsi.exercise.model.Difficulty;
import com.klopsi.exercise.model.Exercise;
import lombok.NoArgsConstructor;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@NoArgsConstructor
public class ExerciseService {

	@PersistenceContext
	private EntityManager em;

	public List<Exercise> findAllExercises() {
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

	public List<Exercise> findExerciseByDifficulty(List<Difficulty> difficulties) {
		return em.createNamedQuery(Exercise.Queries.FIND_BY_DIFFICULTY, Exercise.class)
			.setParameter("difficulties", difficulties)
			.getResultList();
	}

	@Transactional
	public void removeExercise(Exercise exercise) {
		em.remove(em.merge(exercise));
	}

	@Transactional
	public synchronized void saveExercise(Exercise exercise){
		if (exercise.getId() == null) {
			em.persist(exercise);
		} else {
			em.merge(exercise);
		}
	}
}
