package com.klopsi.exercise.resource;

import com.klopsi.answer.model.Answer;
import com.klopsi.exercise.ExerciseService;
import com.klopsi.exercise.model.Exercise;
import com.klopsi.resource.Api;
import com.klopsi.resource.model.EmbeddedResource;
import com.klopsi.resource.model.Link;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

import static com.klopsi.resource.UriHelper.pagedUri;
import static com.klopsi.resource.UriHelper.uri;

@Path("/exercises")
public class ExerciseResource {

	@Context
	private UriInfo info;

	@Inject
	private ExerciseService exerciseService;

	/**
	 * Fetch all exercises available.
	 *
	 * @return all exercises
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("")	// required for URL generation
	public Response getAllExercises(@QueryParam("page") @DefaultValue("0") Integer page, @QueryParam("limit") @DefaultValue("10") Integer limit){
		if(limit <= 0){
			limit = 1;
		}
		List<Exercise> exercises = exerciseService.findAllExercises(page * limit, limit);

		exercises.forEach(exercise -> exercise.getLinks().put(
			"self",
			Link.builder().href(uri(info, ExerciseResource.class, "getExercise", exercise.getId())).build()
		));

		long size = exerciseService.countExercises();

		EmbeddedResource.EmbeddedResourceBuilder<List<Exercise>> builder = EmbeddedResource.<List<Exercise>>builder()
			.embedded("exercises", exercises);

		builder.link(
			"api",
			Link.builder().href(uri(info, Api.class, "getApi")).build());

		builder.link(
			"self",
			Link.builder().href(uri(info, ExerciseResource.class, "getAllExercises")).build());

		builder.link(
			"first",
			Link.builder().href(pagedUri(info, ExerciseResource.class, "getAllExercises", 0, limit)).build());

		//builder.link(
		//	"last",
		//	Link.builder().href(pagedUri(info, ExerciseResource.class, "getAllExercises", max(size / limit - 1, 0), limit)).build());

		if (page < size / limit - 1) {
			builder.link(
				"next",
				Link.builder().href(pagedUri(info, ExerciseResource.class, "getAllExercises", page + 1, limit)).build());
		}

		if (page > 0) {
			builder.link(
				"previous",
				Link.builder().href(pagedUri(info, ExerciseResource.class, "getAllExercises", page - 1, limit)).build());
		}

		EmbeddedResource<List<Exercise>> embedded = builder.build();
		return Response.ok(embedded).build();
	}


	/**
	 * Find single book
	 *
	 * @param exerciseId exercise id
	 * @return response with exercise entity or 404 code
	 */
	@GET
	@Path("{exerciseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExercise(@PathParam("exerciseId") int exerciseId){
		Exercise exercise = exerciseService.findExercise(exerciseId);
		if (exercise != null) {
			exercise.getLinks().put(
				"self",
				Link.builder().href(uri(info, ExerciseResource.class, "getExercise", exercise.getId())).build());

			exercise.getLinks().put(
				"answers",
				Link.builder().href(uri(info, ExerciseResource.class, "getExerciseAnswers", exercise.getId())).build());

			exercise.getLinks().put(
				"exercises",
				Link.builder().href(uri(info, ExerciseResource.class, "getAllExercises")).build());

			exercise.getLinks().put(
				"deleteExercise",
				Link.builder().method(HttpMethod.DELETE).href(uri(info, ExerciseResource.class, "deleteExercise", exercise.getId())).build());

			return Response.ok(exercise).build();
		}
		else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}


	@GET
	@Path("{exerciseId}/answers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExerciseAnswers(@PathParam("exerciseId") int exerciseId) {
		Exercise exercise = exerciseService.findExercise(exerciseId);
		if (exercise != null) {
			EmbeddedResource<List<Answer>> embedded = EmbeddedResource.<List<Answer>>builder()
				.embedded("answers", exercise.getAnswers())
				.link(
					"exercise",
					Link.builder().href(uri(info, ExerciseResource.class, "getExercise", exercise.getId())).build())

				.link(
					"self",
					Link.builder().href(uri(info, ExerciseResource.class, "getExerciseAnswers", exercise.getId())).build())
				.build();
			return Response.ok(embedded).build();
		}
		else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}


	/**
	 * Saves new exercise
	 * @param exercise new exercise
	 * @return response with 201 code and new object url
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveExercise(Exercise exercise) {
		exerciseService.saveExercise(exercise);
		return Response
			.created(uri(ExerciseResource.class, "getExercise", exercise.getId())).build();
	}

	/**
	 * updates single exercise
	 * @param exerciseId exercise id
	 * @param exercise exercise edited
	 * @return response 200 code or 404 when exercise does not exist or 400 when exercise ids mismatch
	 */
	@PUT
	@Path("{exerciseId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateExercise(@PathParam("exerciseId") int exerciseId, Exercise exercise) {
		Exercise originalExercise = exerciseService.findExercise(exerciseId);
		if (originalExercise == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		else if (originalExercise.getId() != exercise.getId()) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		else {
			exercise.setAnswers(originalExercise.getAnswers());
			exerciseService.saveExercise(exercise);
			return Response.ok().build();
		}
	}

	/**
	 * Delete single exercise
	 * @param exerciseId exercise id
	 * @return response 204 code or 404 when exercise with given id does not exist
	 */
	@DELETE
	@Path("{exerciseId}")
	public Response deleteExercise(@PathParam("exerciseId") int exerciseId) {
		Exercise exercise = exerciseService.findExercise(exerciseId);
		if (exercise == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		else {
			exerciseService.removeExercise(exercise);
			return Response.noContent().build();
		}
	}
}
