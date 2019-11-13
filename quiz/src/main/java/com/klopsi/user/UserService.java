package com.klopsi.user;

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

	public List<User> findAllUsers() {
		if(securityContext.isUserInRole("USER")){
			return em.createNamedQuery(User.Queries.FIND_ALL, User.class).getResultList();
		}
		else {
			throw new AccessControlException("Access denied");
		}
	}

	public User findUser(int id) {
		if(securityContext.isUserInRole("USER")){
			return em.find(User.class, id);
		}
		else {
			throw new AccessControlException("Access denied");
		}
	}

	@Transactional
	public void removeUser(User user) {
		if(securityContext.isUserInRole("ADMIN")){
			em.remove(em.merge(user));
		}
		else {
			throw new AccessControlException("Access denied");
		}
	}

	@Transactional
	public void saveUser(User user){
		if(securityContext.isUserInRole("USER")){
			if(user.getId() == null) {
				em.persist(user);
			}
			else {
				em.merge(user);
			}
		}
		else {
			throw new AccessControlException("Access denied");
		}
	}
}
