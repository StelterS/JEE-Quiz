package com.klopsi.answer.resource;

import com.klopsi.answer.AnswerService;
import com.klopsi.answer.model.Answer;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Collection;

@Path("answers")
public class AnswerResource {

	@Inject
	private AnswerService answerService;


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Answer> getAllAnswers() {
		return answerService.findAllAnswers();
	}

	@GET
	@Path("{answerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAnswer(@PathParam("answerId") int answerId) {
		Answer answer = answerService.findAnswer(answerId);
		if (answer != null) {
			return Response.ok(answer).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveAnswer(Answer answer) {
		answerService.saveAnswer(answer);
		return Response
			.created(UriBuilder.fromResource(AnswerResource.class).path(AnswerResource.class, "getAnswer").build(answer.getId()))
			.build();
	}

	@PUT
	@Path("{answerId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateAnswer(@PathParam("answerId") int answerId, Answer answer) {
		Answer originalAnswer = answerService.findAnswer(answerId);
		if (originalAnswer == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else if (originalAnswer.getId() != answer.getId()) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		} else {
			answerService.deleteAnswerFromExerciseList(originalAnswer);
			answerService.deleteAnswerFromUserList(originalAnswer);
			answerService.saveAnswer(answer);
			return Response.ok().build();
		}
	}

	@DELETE
	@Path("{answerId}")
	public Response deleteAnswer(@PathParam("answerId") int answerId) {
		Answer answer = answerService.findAnswer(answerId);
		if (answer == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			answerService.removeAnswer(answer);
			return Response.noContent().build();
		}
	}

}
