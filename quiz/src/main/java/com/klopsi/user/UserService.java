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

	@Transactional
	public void init() {
		User user1 = new User("Albert", "Einstein", LocalDate.of(1879, Month.MARCH, 14));
		User user2 = new User("Maria", "Skłodowska-Curie", LocalDate.of(1867, Month.NOVEMBER, 7));
		User user3 = new User("Isaac", "Newton", LocalDate.of(1643, Month.JANUARY, 4));
		User user4 = new User("Stephen", "Hawking", LocalDate.of(1942, Month.JANUARY, 8));
		User user5 = new User("Alan", "Turing", LocalDate.of(1912, Month.JUNE, 23));

		em.persist(user1);
		em.persist(user2);
		em.persist(user3);
		em.persist(user4);
		em.persist(user5);
	}

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
