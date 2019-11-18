package com.klopsi.user;

import com.klopsi.user.interceptor.CheckUser;
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
public class UserService {
	@PersistenceContext
	private EntityManager em;

	@Inject
	private HttpServletRequest securityContext;

	@CheckUser
	public List<User> findAllUsers() {
		return em.createNamedQuery(User.Queries.FIND_ALL, User.class).getResultList();
	}

	@CheckUser
	public User findUser(int id) {
		if(securityContext.isUserInRole("USER")){
			return em.find(User.class, id);
		}
		else {
			throw new AccessControlException("Access denied");
		}
	}

	@CheckUser
	public User findUserByLogin(String login) {
		return em.createNamedQuery(User.Queries.FIND_BY_LOGIN, User.class)
				.setParameter("login", securityContext.getUserPrincipal().getName())
				.getSingleResult();
	}

	@CheckUser
	public List<String> findUserLogins() {
			return em.createNamedQuery(User.Queries.FIND_ALL_LOGINS, String.class).getResultList();
	}

	@CheckUser
	@Transactional
	public void removeUser(User user) {
		em.remove(em.merge(user));
	}

	@CheckUser
	@Transactional
	public void saveUser(User user){
		if(user.getId() == null) {
			em.persist(user);
		}
		else {
			em.merge(user);
		}
	}
}
