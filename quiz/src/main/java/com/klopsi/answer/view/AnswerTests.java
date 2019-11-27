package com.klopsi.answer.view;

import com.klopsi.answer.AnswerService;
import com.klopsi.user.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AnswerTests {
    private AnswerService service;

    @Inject
    public AnswerTests(AnswerService service){
        this.service = service;
    }

    public void runTests()
    {

    }
}
