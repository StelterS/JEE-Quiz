package com.klopsi.answer;

import com.klopsi.answer.model.Answer;
import com.klopsi.answer.interceptor.CheckAnswerUser;
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

	@CheckAnswerUser
	public List<Answer> findAllAnswers() {
		if(securityContext.isUserInRole("ADMIN") || securityContext.isUserInRole("MODERATOR")){
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

	@CheckAnswerUser
	public synchronized Answer findAnswer(int id) {
		return em.find(Answer.class, id);
	}

	@CheckAnswerUser
	@Transactional
	public void removeAnswer(Answer answer) {
		em.remove(em.merge(answer));
	}

	@CheckAnswerUser
	@Transactional
	public synchronized void saveAnswer(Answer answer){
		if (answer.getId() == null) {
			em.persist(answer);
		} else {
			em.merge(answer);
		}
	}
}
