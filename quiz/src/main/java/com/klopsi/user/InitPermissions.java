package com.klopsi.user;

import com.klopsi.user.model.RolePermission;
import com.klopsi.user.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class InitPermissions {
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

		/*
		* UserService permissions
		* */

		// user permissions
		RolePermission perm1 = RolePermission.builder()
			.role(User.Roles.USER)
			.operation("findAllUsers")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm2 = RolePermission.builder()
			.role(User.Roles.USER)
			.operation("findUser")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm3 = RolePermission.builder()
			.role(User.Roles.USER)
			.operation("findUserByLogin")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm4 = RolePermission.builder()
			.role(User.Roles.USER)
			.operation("findUserLogins")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm5 = RolePermission.builder()
			.role(User.Roles.USER)
			.operation("removeUser")
			.permission(RolePermission.Permission.DENIED).build();

		RolePermission perm6 = RolePermission.builder()
			.role(User.Roles.USER)
			.operation("saveUser")
			.permission(RolePermission.Permission.GRANTED).build();

		// admin permissions
		RolePermission perm7 = RolePermission.builder()
			.role(User.Roles.ADMIN)
			.operation("findAllUsers")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm8 = RolePermission.builder()
			.role(User.Roles.ADMIN)
			.operation("findUser")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm9 = RolePermission.builder()
			.role(User.Roles.ADMIN)
			.operation("findUserByLogin")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm10 = RolePermission.builder()
			.role(User.Roles.ADMIN)
			.operation("findUserLogins")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm11 = RolePermission.builder()
			.role(User.Roles.ADMIN)
			.operation("removeUser")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm12 = RolePermission.builder()
			.role(User.Roles.ADMIN)
			.operation("saveUser")
			.permission(RolePermission.Permission.GRANTED).build();



		// moderator permissions
		RolePermission perm13 = RolePermission.builder()
			.role(User.Roles.MODERATOR)
			.operation("findAllUsers")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm14 = RolePermission.builder()
			.role(User.Roles.MODERATOR)
			.operation("findUser")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm15 = RolePermission.builder()
			.role(User.Roles.MODERATOR)
			.operation("findUserByLogin")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm16 = RolePermission.builder()
			.role(User.Roles.MODERATOR)
			.operation("findUserLogins")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm17 = RolePermission.builder()
			.role(User.Roles.MODERATOR)
			.operation("removeUser")
			.permission(RolePermission.Permission.DENIED).build();

		RolePermission perm18 = RolePermission.builder()
			.role(User.Roles.MODERATOR)
			.operation("saveUser")
			.permission(RolePermission.Permission.GRANTED).build();


		// anonymous permissions
		RolePermission perm19 = RolePermission.builder()
			.role(User.Roles.ANONYMOUS)
			.operation("findAllUsers")
			.permission(RolePermission.Permission.DENIED).build();

		RolePermission perm20 = RolePermission.builder()
			.role(User.Roles.ANONYMOUS)
			.operation("findUser")
			.permission(RolePermission.Permission.DENIED).build();

		RolePermission perm21 = RolePermission.builder()
			.role(User.Roles.ANONYMOUS)
			.operation("findUserByLogin")
			.permission(RolePermission.Permission.DENIED).build();

		RolePermission perm22 = RolePermission.builder()
			.role(User.Roles.ANONYMOUS)
			.operation("findUserLogins")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm23 = RolePermission.builder()
			.role(User.Roles.ANONYMOUS)
			.operation("removeUser")
			.permission(RolePermission.Permission.DENIED).build();

		RolePermission perm24 = RolePermission.builder()
			.role(User.Roles.ANONYMOUS)
			.operation("saveUser")
			.permission(RolePermission.Permission.GRANTED).build();

		/*
		 * ExerciseService permissions
		 * */

		// user permissions
		RolePermission perm25 = RolePermission.builder()
			.role(User.Roles.USER)
			.operation("findAllExercises")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm26 = RolePermission.builder()
			.role(User.Roles.USER)
			.operation("findExercise")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm27 = RolePermission.builder()
			.role(User.Roles.USER)
			.operation("countExercises")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm28 = RolePermission.builder()
			.role(User.Roles.USER)
			.operation("findExerciseByDifficulty")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm29 = RolePermission.builder()
			.role(User.Roles.USER)
			.operation("removeExercise")
			.permission(RolePermission.Permission.DENIED).build();

		RolePermission perm30 = RolePermission.builder()
			.role(User.Roles.USER)
			.operation("saveExercise")
			.permission(RolePermission.Permission.DENIED).build();

		// admin permissions
		RolePermission perm31 = RolePermission.builder()
			.role(User.Roles.ADMIN)
			.operation("findAllExercises")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm32 = RolePermission.builder()
			.role(User.Roles.ADMIN)
			.operation("countExercises")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm33 = RolePermission.builder()
			.role(User.Roles.ADMIN)
			.operation("findExercise")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm34 = RolePermission.builder()
			.role(User.Roles.ADMIN)
			.operation("findExerciseByDifficulty")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm35 = RolePermission.builder()
			.role(User.Roles.ADMIN)
			.operation("removeExercise")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm36 = RolePermission.builder()
			.role(User.Roles.ADMIN)
			.operation("saveExercise")
			.permission(RolePermission.Permission.GRANTED).build();



		// moderator permissions
		RolePermission perm37 = RolePermission.builder()
			.role(User.Roles.MODERATOR)
			.operation("findAllExercises")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm38 = RolePermission.builder()
			.role(User.Roles.MODERATOR)
			.operation("countExercises")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm39 = RolePermission.builder()
			.role(User.Roles.MODERATOR)
			.operation("findExercise")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm40 = RolePermission.builder()
			.role(User.Roles.MODERATOR)
			.operation("findExerciseByDifficulty")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm41 = RolePermission.builder()
			.role(User.Roles.MODERATOR)
			.operation("removeExercise")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm42 = RolePermission.builder()
			.role(User.Roles.MODERATOR)
			.operation("saveExercise")
			.permission(RolePermission.Permission.GRANTED).build();


		// anonymous permissions
		RolePermission perm43 = RolePermission.builder()
			.role(User.Roles.ANONYMOUS)
			.operation("findAllExercises")
			.permission(RolePermission.Permission.DENIED).build();

		RolePermission perm44 = RolePermission.builder()
			.role(User.Roles.ANONYMOUS)
			.operation("countExercises")
			.permission(RolePermission.Permission.DENIED).build();

		RolePermission perm45 = RolePermission.builder()
			.role(User.Roles.ANONYMOUS)
			.operation("findExercise")
			.permission(RolePermission.Permission.DENIED).build();

		RolePermission perm46 = RolePermission.builder()
			.role(User.Roles.ANONYMOUS)
			.operation("findExerciseByDifficulty")
			.permission(RolePermission.Permission.DENIED).build();

		RolePermission perm47 = RolePermission.builder()
			.role(User.Roles.ANONYMOUS)
			.operation("removeExercise")
			.permission(RolePermission.Permission.DENIED).build();

		RolePermission perm48 = RolePermission.builder()
			.role(User.Roles.ANONYMOUS)
			.operation("saveExercise")
			.permission(RolePermission.Permission.DENIED).build();

		/*
		 * AnswerService permissions
		 * */

		// user permissions
		RolePermission perm49 = RolePermission.builder()
			.role(User.Roles.USER)
			.operation("findAllAnswers")
			.permission(RolePermission.Permission.DENIED).build();

		RolePermission perm50 = RolePermission.builder()
			.role(User.Roles.USER)
			.operation("findAnswer")
			.permission(RolePermission.Permission.IF_OWNER).build();

		RolePermission perm51 = RolePermission.builder()
			.role(User.Roles.USER)
			.operation("removeAnswer")
			.permission(RolePermission.Permission.IF_OWNER).build();

		RolePermission perm52 = RolePermission.builder()
			.role(User.Roles.USER)
			.operation("saveAnswer")
			.permission(RolePermission.Permission.IF_OWNER).build();


		// admin permissions
		RolePermission perm53 = RolePermission.builder()
			.role(User.Roles.ADMIN)
			.operation("findAllAnswers")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm54 = RolePermission.builder()
			.role(User.Roles.ADMIN)
			.operation("findAnswer")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm55 = RolePermission.builder()
			.role(User.Roles.ADMIN)
			.operation("removeAnswer")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm56 = RolePermission.builder()
			.role(User.Roles.ADMIN)
			.operation("saveAnswer")
			.permission(RolePermission.Permission.GRANTED).build();



		// moderator permissions
		RolePermission perm57 = RolePermission.builder()
			.role(User.Roles.MODERATOR)
			.operation("findAllAnswers")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm58 = RolePermission.builder()
			.role(User.Roles.MODERATOR)
			.operation("findAnswer")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm59 = RolePermission.builder()
			.role(User.Roles.MODERATOR)
			.operation("removeAnswer")
			.permission(RolePermission.Permission.GRANTED).build();

		RolePermission perm60 = RolePermission.builder()
			.role(User.Roles.MODERATOR)
			.operation("saveAnswer")
			.permission(RolePermission.Permission.GRANTED).build();


		// anonymous permissions
		RolePermission perm61 = RolePermission.builder()
			.role(User.Roles.ANONYMOUS)
			.operation("findAllAnswers")
			.permission(RolePermission.Permission.DENIED).build();

		RolePermission perm62 = RolePermission.builder()
			.role(User.Roles.ANONYMOUS)
			.operation("findAnswer")
			.permission(RolePermission.Permission.DENIED).build();

		RolePermission perm63 = RolePermission.builder()
			.role(User.Roles.ANONYMOUS)
			.operation("removeAnswer")
			.permission(RolePermission.Permission.DENIED).build();

		RolePermission perm64 = RolePermission.builder()
			.role(User.Roles.ANONYMOUS)
			.operation("saveAnswer")
			.permission(RolePermission.Permission.DENIED).build();


		em.persist(perm1);
		em.persist(perm2);
		em.persist(perm3);
		em.persist(perm4);
		em.persist(perm5);
		em.persist(perm6);
		em.persist(perm7);
		em.persist(perm8);
		em.persist(perm9);
		em.persist(perm10);
		em.persist(perm11);
		em.persist(perm12);
		em.persist(perm13);
		em.persist(perm14);
		em.persist(perm15);
		em.persist(perm16);
		em.persist(perm17);
		em.persist(perm18);
		em.persist(perm19);
		em.persist(perm20);
		em.persist(perm21);
		em.persist(perm22);
		em.persist(perm23);
		em.persist(perm24);
		em.persist(perm25);
		em.persist(perm26);
		em.persist(perm27);
		em.persist(perm28);
		em.persist(perm29);
		em.persist(perm30);
		em.persist(perm31);
		em.persist(perm32);
		em.persist(perm33);
		em.persist(perm34);
		em.persist(perm35);
		em.persist(perm36);
		em.persist(perm37);
		em.persist(perm38);
		em.persist(perm39);
		em.persist(perm40);
		em.persist(perm41);
		em.persist(perm42);
		em.persist(perm43);
		em.persist(perm44);
		em.persist(perm45);
		em.persist(perm46);
		em.persist(perm47);
		em.persist(perm48);
		em.persist(perm49);
		em.persist(perm50);
		em.persist(perm51);
		em.persist(perm52);
		em.persist(perm53);
		em.persist(perm54);
		em.persist(perm55);
		em.persist(perm56);
		em.persist(perm57);
		em.persist(perm58);
		em.persist(perm59);
		em.persist(perm60);
		em.persist(perm61);
		em.persist(perm62);
		em.persist(perm63);
		em.persist(perm64);
	}
}
