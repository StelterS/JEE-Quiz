package com.klopsi.answer;

import com.klopsi.answer.model.Answer;
import lombok.NoArgsConstructor;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

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
		em.remove(em.merge(answer));
	}

	@Transactional
	public synchronized void saveAnswer(Answer answer){
		if (answer.getId() == null) {
			em.persist(answer);
		} else {
			em.merge(answer);
		}
	}
}
