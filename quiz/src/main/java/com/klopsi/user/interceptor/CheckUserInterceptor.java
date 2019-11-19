package com.klopsi.user.interceptor;

import com.klopsi.user.model.RolePermission;
import com.klopsi.user.model.User;
import lombok.extern.java.Log;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.management.relation.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.logging.Level;

@Interceptor
@CheckUser
@Priority(100)
@Log
public class CheckUserInterceptor {
	@PersistenceContext
	private EntityManager em;

	@Inject
	private HttpServletRequest securityContext;

	@AroundInvoke
	public Object invoke(InvocationContext context) throws Exception {
		log.log(Level.INFO, "User " + securityContext.getUserPrincipal().getName() + " calls "
			+ context.getMethod().getName() + " with params " + Arrays.toString(context.getParameters()));

		RolePermission.Permission permission = RolePermission.Permission.DENIED;
		if(securityContext.getUserPrincipal() == null) {
			permission = checkPermission(User.Roles.ANONYMOUS, context.getMethod().getName());
		}
		else if(securityContext.isUserInRole(User.Roles.ADMIN)) {
			permission = checkPermission(User.Roles.ADMIN, context.getMethod().getName());
		}
		else if(securityContext.isUserInRole(User.Roles.MODERATOR)) {
			permission = checkPermission(User.Roles.MODERATOR, context.getMethod().getName());
		}
		else if(securityContext.isUserInRole(User.Roles.USER)) {
			permission = checkPermission(User.Roles.USER, context.getMethod().getName());
		}

		if(permission == RolePermission.Permission.DENIED) {
			throw new AccessDeniedException("Access denied");
		}
		else if(permission == RolePermission.Permission.GRANTED) {
			return context.proceed();
		}
		else if(permission == RolePermission.Permission.IF_OWNER && securityContext.getUserPrincipal() == null) {
			// anonymous does not own anything
			throw new AccessDeniedException("Access denied");
		}
		// else permission == IF_OWNER
		User user = null;
		switch (context.getMethod().getName())
		{
			case "removeUser":
			case "saveUser":
				// by user
				user = (User)context.getParameters()[0];
				break;
			case "findUser":
				// by id
				user = em.find(User.class, context.getParameters()[0]);
				break;
			case "findAllUsers":
			case "findUserLogins":
			case "findUserByLogin":
				return context.proceed();
			default:
				throw new AccessDeniedException("Access denied");
		}
		if((user == null) || (!user.getLogin().equals(securityContext.getUserPrincipal().getName()))){
			throw new AccessDeniedException("Access denied");
		}
		return context.proceed();
	}


	private RolePermission.Permission checkPermission(String role, String operation) {
		return em.createNamedQuery(RolePermission.Queries.FIND_BY_METHOD_AND_ROLE, RolePermission.class)
			.setParameter("role", role)
			.setParameter("operation", operation)
			.getSingleResult().getPermission();
	}
}
