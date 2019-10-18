package com.klopsi.exercise.resource;

import com.klopsi.exercise.ExerciseService;
import com.klopsi.exercise.model.Exercise;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Collection;

@Path("/exercises")
public class ExerciseResource {

	@Inject
	private ExerciseService exerciseService;

	/**
	 * Fetch all exercises available.
	 *
	 * @return all exercises
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Exercise> getAllExercises(){
		return exerciseService.findAllExercises();
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
			return Response.ok(exercise).build();
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
			.created(UriBuilder.fromResource(ExerciseResource.class).path(ExerciseResource.class, "getExercise").build(exercise.getId()))
			.build();
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
