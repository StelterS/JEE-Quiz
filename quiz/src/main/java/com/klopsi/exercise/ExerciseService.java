package com.klopsi.exercise;

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

	public List<Exercise> findAllExercises() {
		if(securityContext.isUserInRole("USER")){
			return em.createNamedQuery(Exercise.Queries.FIND_ALL, Exercise.class).getResultList();
		}
		else {
			throw new AccessControlException("Access denied");
		}
	}

	public List<Exercise> findAllExercises(int offset, int limit) {
		if(securityContext.isUserInRole("USER")){
			return em.createNamedQuery(Exercise.Queries.FIND_ALL, Exercise.class)
					.setFirstResult(offset)
					.setMaxResults(limit)
					.getResultList();		}
		else {
			throw new AccessControlException("Access denied");
		}
	}

	public long countExercises() {
		if(securityContext.isUserInRole("USER")){
			return em.createNamedQuery(Exercise.Queries.COUNT, Long.class).getSingleResult();
		}
		else {
			throw new AccessControlException("Access denied");
		}
	}

	public Exercise findExercise(int id) {
		if(securityContext.isUserInRole("USER")){
			return em.find(Exercise.class, id);
		}
		else {
			throw new AccessControlException("Access denied");
		}
	}

	public List<Exercise> findExerciseByDifficulty(List<Difficulty> difficulties) {
		if(securityContext.isUserInRole("USER")){
			return em.createNamedQuery(Exercise.Queries.FIND_BY_DIFFICULTY, Exercise.class)
					.setParameter("difficulties", difficulties)
					.getResultList();
		}
		else {
			throw new AccessControlException("Access denied");
		}
	}

	@Transactional
	public void removeExercise(Exercise exercise) {
		if(securityContext.isUserInRole("ADMIN") || securityContext.isUserInRole("MODERATOR")){
			em.remove(em.merge(exercise));
		}
		else {
			throw new AccessControlException("Access denied");
		}
	}

	@Transactional
	public synchronized void saveExercise(Exercise exercise){
		if(securityContext.isUserInRole("ADMIN") || securityContext.isUserInRole("MODERATOR")){
			if (exercise.getId() == null) {
				em.persist(exercise);
			} else {
				em.merge(exercise);
			}
		}
		else {
			throw new AccessControlException("Access denied");
		}
	}
}
