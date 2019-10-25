package com.klopsi.answer.resource;

import com.klopsi.answer.AnswerService;
import com.klopsi.answer.model.Answer;
import com.klopsi.exercise.model.Exercise;
import com.klopsi.resource.Api;
import com.klopsi.resource.model.EmbeddedResource;
import com.klopsi.resource.model.Link;
import com.klopsi.user.model.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

import static com.klopsi.resource.UriHelper.uri;

@Path("answers")
public class AnswerResource {

	@Context
	private UriInfo info;


	@Inject
	private AnswerService answerService;


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("")
	public Response getAllAnswers() {
		List<Answer> answers = answerService.findAllAnswers();

		answers.forEach(answer -> answer.getLinks().put(
			"self",
			Link.builder().href(uri(info, AnswerResource.class, "getAnswer", answer.getId())).build())
		);

		EmbeddedResource.EmbeddedResourceBuilder<List<Answer>> builder = EmbeddedResource.<List<Answer>>builder()
			.embedded("answers", answers);

		builder.link(
			"api",
			Link.builder().href(uri(info, Api.class, "getApi")).build());

		builder.link(
			"self",
			Link.builder().href(uri(info, AnswerResource.class, "getAllAnswers")).build());

		EmbeddedResource<List<Answer>> embedded = builder.build();
		return Response.ok(embedded).build();
	}

	@GET
	@Path("{answerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAnswer(@PathParam("answerId") int answerId) {
		Answer answer = answerService.findAnswer(answerId);
		if (answer != null) {
			answer.getLinks().put(
				"self",
				Link.builder().href(uri(info, AnswerResource.class, "getAnswer", answer.getId())).build());

			answer.getLinks().put(
				"exercise",
				Link.builder().href(uri(info, AnswerResource.class, "getAnswerExercise", answer.getId())).build());

			answer.getLinks().put(
				"user",
				Link.builder().href(uri(info, AnswerResource.class, "getAnswerUser", answer.getId())).build());

			answer.getLinks().put(
				"answers",
				Link.builder().href(uri(info, AnswerResource.class, "getAllAnswers")).build());
			return Response.ok(answer).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("{answerId}/exercise")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAnswerExercise(@PathParam("answerId") int answerId) {
		Answer answer = answerService.findAnswer(answerId);
		if (answer != null) {
			EmbeddedResource<Exercise> embedded = EmbeddedResource.<Exercise>builder()
				.embedded("exercise", answer.getExercise())
				.link(
					"answer",
					Link.builder().href(uri(info, AnswerResource.class, "getAnswer", answer.getId())).build())

				.link(
					"self",
					Link.builder().href(uri(info, AnswerResource.class, "getAnswerExercise", answer.getId())).build())
				.build();
			return Response.ok(embedded).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("{answerId}/user")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAnswerUser(@PathParam("answerId") int answerId) {
		Answer answer = answerService.findAnswer(answerId);
		if (answer != null) {
			EmbeddedResource<User> embedded = EmbeddedResource.<User>builder()
				.embedded("user", answer.getUser())
				.link(
					"answer",
					Link.builder().href(uri(info, AnswerResource.class, "getAnswer", answer.getId())).build())

				.link(
					"self",
					Link.builder().href(uri(info, AnswerResource.class, "getAnswerUser", answer.getId())).build())
				.build();
			return Response.ok(embedded).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveAnswer(Answer answer) {
		answerService.saveAnswer(answer);
		return Response.created(uri(AnswerResource.class, "getAnswer", answer.getId())).build();
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
