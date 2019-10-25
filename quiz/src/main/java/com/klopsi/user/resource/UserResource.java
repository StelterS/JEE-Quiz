package com.klopsi.user.resource;

import com.klopsi.answer.model.Answer;
import com.klopsi.answer.resource.AnswerResource;
import com.klopsi.resource.Api;
import com.klopsi.resource.model.EmbeddedResource;
import com.klopsi.resource.model.Link;
import com.klopsi.user.UserService;
import com.klopsi.user.model.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

import static com.klopsi.resource.UriHelper.uri;

@Path("/users")
public class UserResource {

	@Context
	private UriInfo info;

	@Inject
	private UserService userService;

	/**
	 * Fetch all users available.
	 *
	 * @return all users
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("")
	public Response getAllUsers(){
		List<User> users = userService.findAllUsers();

		users.forEach(user -> user.getLinks().put(
			"self",
			Link.builder().href(uri(info, UserResource.class, "getUser", user.getId())).build())
		);

		EmbeddedResource.EmbeddedResourceBuilder<List<User>> builder = EmbeddedResource.<List<User>>builder()
			.embedded("users", users);

		builder.link(
			"api",
			Link.builder().href(uri(info, Api.class, "getApi")).build());

		builder.link(
			"self",
			Link.builder().href(uri(info, UserResource.class, "getAllUsers")).build());

		EmbeddedResource<List<User>> embedded = builder.build();
		return Response.ok(embedded).build();
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
	public Response getUser(@PathParam("userId") int userId){
		User user = userService.findUser(userId);
		if (user != null) {
			user.getLinks().put(
				"self",
				Link.builder().href(uri(info, UserResource.class, "getUser", user.getId())).build());

			user.getLinks().put(
				"answers",
				Link.builder().href(uri(info, UserResource.class, "getUserAnswers", user.getId())).build());

			user.getLinks().put(
				"users",
				Link.builder().href(uri(info, UserResource.class, "getAllUsers")).build());
			return Response.ok(user).build();
		}
		else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("{userId}/answers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserAnswers(@PathParam("userId") int userId) {
		User user = userService.findUser(userId);
		if (user != null) {
			EmbeddedResource<List<Answer>> embedded = EmbeddedResource.<List<Answer>>builder()
				.embedded("authors", user.getAnswers())
				.link(
					"user",
					Link.builder().href(uri(info, UserResource.class, "getUser", user.getId())).build())

				.link(
					"self",
					Link.builder().href(uri(info, UserResource.class, "getUserAnswers", user.getId())).build())
				.build();
			return Response.ok(embedded).build();
		} else {
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
	public Response saveUser(User user) {
		userService.saveUser(user);
		return Response.created(uri(UserResource.class, "getUser", user.getId())).build();
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
	public Response updateUser(@PathParam("userId") int userId, User user) {
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
	public Response deleteUser(@PathParam("userId") int userId) {
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
