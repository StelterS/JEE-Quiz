package com.klopsi.user;

import com.klopsi.user.model.User;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;

import static com.klopsi.user.HashUtils.sha256;

@ApplicationScoped
public class InitUser {

    /**
     * Injected EntityManager connected to database specified in the persistence.xml.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * This method will automatically called when the ApplicationScoped will be initialized. Using the @PostConstruct
     * annotated method does not secure transactions.
     *
     * @param init
     */
    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        User admin = User.builder().login("admin").password(sha256("admin")).firstName("Szymon").lastName("Stelter").birthDate(LocalDate.of(1997, Month.AUGUST, 13)).answers(new HashSet<>()).role(User.Roles.ADMIN).role(User.Roles.USER).build();
        User user1 = User.builder().login("user1").password(sha256("user1")).firstName("Albert").lastName("Einstein").birthDate(LocalDate.of(1879, Month.MARCH, 14)).answers(new HashSet<>()).role(User.Roles.USER).build();
        User user2 = User.builder().login("user2").password(sha256("user2")).firstName("Maria").lastName("Skłodowska-Curie").birthDate(LocalDate.of(1867, Month.NOVEMBER, 7)).answers(new HashSet<>()).role(User.Roles.USER).build();
        User user3 = User.builder().login("user3").password(sha256("user3")).firstName("Isaac").lastName("Newton").birthDate(LocalDate.of(1643, Month.JANUARY, 4)).answers(new HashSet<>()).role(User.Roles.USER).build();
        User user4 = User.builder().login("user4").password(sha256("user4")).firstName("Stephen").lastName("Hawking").birthDate(LocalDate.of(1942, Month.JANUARY, 8)).answers(new HashSet<>()).role(User.Roles.USER).build();
        User user5 = User.builder().login("user5").password(sha256("user5")).firstName("Alan").lastName("Turing").birthDate(LocalDate.of(1912, Month.JUNE, 23)).answers(new HashSet<>()).role(User.Roles.USER).build();

        em.persist(user1);
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);
        em.persist(user5);
        em.persist(admin);
    }
}
