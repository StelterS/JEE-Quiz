package com.klopsi.answer;

import com.klopsi.answer.model.Answer;
import com.klopsi.exercise.model.Exercise;
import com.klopsi.user.model.User;
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
public class AnswerService {
	@PersistenceContext
	private EntityManager em;

	@Inject
	private HttpServletRequest securityContext;

	public List<Answer> findAllAnswers() {
		if(securityContext.isUserInRole("ADMIN")){
			return em.createNamedQuery(Answer.Queries.FIND_ALL, Answer.class).getResultList();
		}
		else if(securityContext.isUserInRole("USER")) {
			// user can query only his answers
			return em.createNamedQuery(Answer.Queries.FIND_BY_USER, Answer.class)
				.setParameter("login", securityContext.getUserPrincipal().getName())
				.getResultList();
		}
		else {
			throw new AccessControlException("Access denied");
		}
	}

	public synchronized Answer findAnswer(int id) {
		if(securityContext.isUserInRole("USER")){
			return em.find(Answer.class, id);
		}
		else {
			throw new AccessControlException("Access denied");
		}
	}

	@Transactional
	public void removeAnswer(Answer answer) {
		if(securityContext.isUserInRole("ADMIN")){
			em.remove(em.merge(answer));
		}
		else if(securityContext.isUserInRole("USER")
			&& answer.getUser().getLogin().equals(securityContext.getUserPrincipal().getName())) {
			// user can delete his own answers only
			em.remove(em.merge(answer));
		}
		else {
			throw new AccessControlException("Access denied");
		}
	}

	@Transactional
	public synchronized void saveAnswer(Answer answer){
		if(securityContext.isUserInRole("USER")){
			if (answer.getId() == null) {
				em.persist(answer);
			} else {
				em.merge(answer);
			}		}
		else {
			throw new AccessControlException("Access denied");
		}
	}
}
