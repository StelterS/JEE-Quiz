package com.klopsi.exercise;

import com.klopsi.exercise.model.Difficulty;
import com.klopsi.exercise.model.Exercise;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class InitExercises {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        Exercise exercise1 = new Exercise("Chrzest Polski", "Kiedy odbył się chrzest Polski?", Difficulty.EASY, 10);
        Exercise exercise2 = new Exercise("Polski Maluch", "Kiedy rozpoczęła się produkcja Malucha?", Difficulty.MEDIUM, 25);
        Exercise exercise3 = new Exercise("Kosmos", "W którym roku Neil Armstrong stanął na Księżycu?", Difficulty.EASY, 10);
        Exercise exercise4 = new Exercise("Ewolucja", "Podaj nazwisko osoby, która wymyśliła teorię ewolucji", Difficulty.MEDIUM, 15);
        Exercise exercise5 = new Exercise("Podbój kosmosu", "Kto jako pierwszy poleciał w kosmos?", Difficulty.HARD, 25);
        Exercise exercise6 = new Exercise("Ziemia", "Czy Ziemia jest największą planetą układu słonecznego?", Difficulty.EASY, 5);
        Exercise exercise7 = new Exercise("Dinozaury", "Podaj nazwę najstraszniejszego dinozaura", Difficulty.MEDIUM, 25);
        Exercise exercise8 = new Exercise("Ananasy", "Czy ananasy rosną na drzewach?", Difficulty.MEDIUM, 25);

        em.persist(exercise1);
        em.persist(exercise2);
        em.persist(exercise3);
        em.persist(exercise4);
        em.persist(exercise5);
        em.persist(exercise6);
        em.persist(exercise7);
        em.persist(exercise8);
    }
}
