package com.klopsi.user;

import com.klopsi.user.model.User;
import lombok.NoArgsConstructor;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@ApplicationScoped
@NoArgsConstructor
public class UserService {
	@PersistenceContext
	private EntityManager em;

	public List<User> findAllUsers() {
		return em.createNamedQuery(User.Queries.FIND_ALL, User.class).getResultList();
	}

	public User findUser(int id) {
		return em.find(User.class, id);
	}

	@Transactional
	public void removeUser(User user) {
		em.remove(em.merge(user));
	}

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
