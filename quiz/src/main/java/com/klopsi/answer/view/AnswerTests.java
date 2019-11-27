package com.klopsi.answer.view;

import com.klopsi.answer.AnswerService;
import com.klopsi.exercise.ExerciseService;
import com.klopsi.user.UserService;
import lombok.Getter;

import javax.enterprise.context.RequestScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AnswerTests {
    private AnswerService answerService;
    private UserService userService;
    private ExerciseService exerciseService;

    @Getter
    private String outcome;

    @Inject
    @Push
    private PushContext push;

    @Inject
    public AnswerTests(AnswerService answerService, UserService userService, ExerciseService exerciseService)
    {
        this.answerService = answerService;
        this.userService = userService;
        this.exerciseService = exerciseService;
    }

    public void runTests()
    {
        com.klopsi.answer.tests.AnswerTests answerTests = new com.klopsi.answer.tests.AnswerTests(answerService, userService, exerciseService);
        if (!answerTests.createReadAnswerTest())
        {
            outcome = "createReadAnswerTest Failed";
        }
        if (!answerTests.sortByScoreTest())
        {
            outcome = "sortReadTest Failed";
        }
        if (!answerTests.updateAnswerTest())
        {
            outcome = "updateAnswerTest Failed";
        }
        if (!answerTests.deleteAnswerTest())
        {
            outcome = "deleteAnswerTest Failed";
        }
        if (!answerTests.filterByScore())
        {
            outcome = "filterByScore Failed";
        }
        outcome = "Success";
        push.send("updateOutcome");
    }
}
