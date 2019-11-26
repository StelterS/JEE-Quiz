package com.klopsi.answer;

import com.klopsi.answer.events.AnswerUpdate;
import com.klopsi.answer.model.Answer;
import com.klopsi.answer.interceptor.CheckAnswerUser;
import com.klopsi.exercise.model.Exercise;
import com.klopsi.user.model.User;
import lombok.NoArgsConstructor;
import com.klopsi.answer.model.Answer_;
import com.klopsi.user.model.User_;
import com.klopsi.exercise.model.Exercise_;
import org.primefaces.model.SortOrder;

import javax.ejb.Local;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.nio.file.AccessDeniedException;
import java.security.AccessControlException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
@NoArgsConstructor
public class AnswerService {
	@PersistenceContext
	private EntityManager em;

	@Inject
	private HttpServletRequest securityContext;

	@Inject
	private Event<AnswerUpdate> answerUpdateEvent;

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

	public List<Answer> findAllAnswers(int offset, int limit, Map<Attribute<Answer, ?>, ?> filters, String sortField, SortOrder sortOrder) {
		if (securityContext.isUserInRole(User.Roles.USER)) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Answer> query = cb.createQuery(Answer.class);
			Root<Answer> root = query.from(Answer.class);

			query.select(root);
			List<Predicate> predicates = parsePredicates(filters, cb, root);
			query.where(cb.and(predicates.toArray(new Predicate[0])));

			if(sortField != null && sortOrder != null)
			{
				switch(sortField){
					case "user":
						if(sortOrder == SortOrder.ASCENDING){
							query.orderBy(cb.asc(cb.lower(root.get(Answer_.user).get(User_.login))));
						}
						else {
							query.orderBy(cb.desc(cb.lower(root.get(Answer_.user).get(User_.login))));
						}
						break;
					case "content":
						if(sortOrder == SortOrder.ASCENDING){
							query.orderBy(cb.asc(cb.lower(root.get(Answer_.content))));
						}
						else {
							query.orderBy(cb.desc(cb.lower(root.get(Answer_.content))));
						}
						break;
					case "percent":
						if(sortOrder == SortOrder.ASCENDING){
							query.orderBy(cb.asc(root.get(Answer_.percent)));
						}
						else {
							query.orderBy(cb.desc(root.get(Answer_.percent)));
						}
						break;
					case "exercise":
						if(sortOrder == SortOrder.ASCENDING){
							query.orderBy(cb.asc(cb.lower(root.get(Answer_.exercise).get(Exercise_.title))));
						}
						else {
							query.orderBy(cb.desc(cb.lower(root.get(Answer_.exercise).get(Exercise_.title))));
						}
						break;
					case "lastModificationDate":
						if(sortOrder == SortOrder.ASCENDING){
							query.orderBy(cb.asc(root.get(Answer_.lastModificationDate)));
						}
						else {
							query.orderBy(cb.desc(root.get(Answer_.lastModificationDate)));
						}
						break;
				}
			}

			return em.createQuery(query)
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();
		}
		else {
			throw new AccessControlException("Access denied");
		}
	}

	private List<Predicate> parsePredicates(Map<Attribute<Answer, ?>, ?> filters, CriteriaBuilder cb, Root<Answer> root) {
		List<Predicate> predicates = new ArrayList<>();

		filters.forEach((key, value) -> {
			if (key instanceof SingularAttribute) {
				@SuppressWarnings("unchecked")
				SingularAttribute<Answer, ?> attribute = (SingularAttribute<Answer, ?>)key;
				if (key.getJavaType() == String.class) {
					@SuppressWarnings("unchecked")
					SingularAttribute<Answer, String> stringAttribute = (SingularAttribute<Answer, String>)attribute;
					predicates.add(cb.like(cb.lower(root.get(stringAttribute)), "%" + ((String)value).toLowerCase() + "%"));
				}
				else if (key.getJavaType() == User.class) {
					@SuppressWarnings("unchecked")
					SingularAttribute<Answer, String> stringAttribute = (SingularAttribute<Answer, String>)attribute;
					predicates.add(cb.like(cb.lower(root.get(Answer_.user).get(User_.login)), "%" + ((String)value).toLowerCase() + "%"));
				}
				else if (key.getJavaType() == Exercise.class) {
					@SuppressWarnings("unchecked")
					SingularAttribute<Answer, String> stringAttribute = (SingularAttribute<Answer, String>)attribute;
					predicates.add(cb.like(cb.lower(root.get(Answer_.exercise).get(Exercise_.title)), "%" + ((String)value).toLowerCase() + "%"));
				}
				else if (key.getJavaType() == LocalDateTime.class) {
					@SuppressWarnings("unchecked")
					SingularAttribute<Answer, LocalDateTime> localDateTimeAttribute = (SingularAttribute<Answer, LocalDateTime>)attribute;
					LocalDateTime time = LocalDateTime.parse((CharSequence) value);
					predicates.add(cb.equal(root.get(localDateTimeAttribute), time));
				}
				else {
					predicates.add(cb.equal(root.get(attribute), value));
				}
			}
		});
		return predicates;
	}

	public long countAnswers(Map<Attribute<Answer, ?>, ?> filters) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		Root<Answer> root = query.from(Answer.class);

		query.select(cb.count(root));

		List<Predicate> predicates = parsePredicates(filters, cb, root);

		query.where(cb.and(predicates.toArray(new Predicate[0])));

		return em.createQuery(query)
			.getSingleResult();
	}

	@CheckAnswerUser
	public List<Answer> findAllAnswersByModificationDate() {
		return em.createNamedQuery(Answer.Queries.FIND_BY_MODIFICATION_DATE, Answer.class).getResultList();
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
		answerUpdateEvent.fire(new AnswerUpdate(answer));
	}
}
