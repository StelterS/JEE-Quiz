package com.klopsi.user.resource;

import com.klopsi.user.UserService;
import com.klopsi.user.model.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Collection;

@Path("/users")
public class UserResource {
	@Inject
	private UserService userService;

	/**
	 * Fetch all users available.
	 *
	 * @return all users
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAllExercises(){
		return userService.findAllUsers();
	}


	/**
	 * Find single user
	 *
	 * @param userId user id
	 * @return response with user entity or 404 code
	 */
	@GET
	@Path("{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExercise(@PathParam("userId") int userId){
		User user = userService.findUser(userId);
		if (user != null) {
			return Response.ok(user).build();
		}
		else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	/**
	 * Saves new user
	 * @param user new user
	 * @return response with 201 code and new object url
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveExercise(User user) {
		userService.saveUser(user);
		return Response
			.created(UriBuilder.fromResource(UserResource.class).path(UserResource.class, "getUser").build(user.getId()))
			.build();
	}

	/**
	 * updates single user
	 * @param userId user id
	 * @param user user edited
	 * @return response 200 code or 404 when exercise does not exist or 400 when exercise ids mismatch
	 */
	@PUT
	@Path("{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateExercise(@PathParam("userId") int userId, User user) {
		User originalUser = userService.findUser(userId);
		if (originalUser == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		else if (originalUser.getId() != user.getId()) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		else {
			user.setAnswers(originalUser.getAnswers());
			userService.saveUser(user);
			return Response.ok().build();
		}
	}

	/**
	 * Delete single user
	 * @param userId user id
	 * @return response 204 code or 404 when user with given id does not exist
	 */
	@DELETE
	@Path("{userId}")
	public Response deleteExercise(@PathParam("userId") int userId) {
		User user = userService.findUser(userId);
		if (user == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		else {
			userService.removeUser(user);
			return Response.noContent().build();
		}
	}
}
