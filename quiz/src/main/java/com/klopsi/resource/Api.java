package com.klopsi.resource;

import com.klopsi.answer.resource.AnswerResource;
import com.klopsi.exercise.resource.ExerciseResource;
import com.klopsi.resource.model.EmbeddedResource;
import com.klopsi.resource.model.Link;
import com.klopsi.user.resource.UserResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/")
public class Api {

	@Context
	private UriInfo info;

	@Path("/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getApi() {
		EmbeddedResource<Void> embedded = EmbeddedResource.<Void>builder()
			.link("exercises", Link.builder().href(
				info.getBaseUriBuilder()
					.path(ExerciseResource.class)
					.path(ExerciseResource.class, "getAllExercises")
					.build())
				.build())
			.link("answers", Link.builder().href(
				info.getBaseUriBuilder()
					.path(AnswerResource.class)
					.path(AnswerResource.class, "getAllAnswers")
					.build())
				.build())
			.link("users", Link.builder().href(
				info.getBaseUriBuilder()
					.path(UserResource.class)
					.path(UserResource.class, "getAllUsers")
					.build())
				.build())
			.link("self", Link.builder().href(
				info.getBaseUriBuilder()
					.path(Api.class)
					.path(Api.class, "getApi")
					.build())
				.build())
			.build();
		return Response.ok(embedded).build();
	}


}
