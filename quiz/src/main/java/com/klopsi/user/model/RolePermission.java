package com.klopsi.user.model;

import lombok.*;

import javax.management.relation.Role;
import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode()
@ToString()
@Entity
@Table(name = "permissions")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NamedQuery(name = RolePermission.Queries.FIND_BY_METHOD_AND_ROLE,
		query = "select r from RolePermission r where r.role = :role and r.operation = :operation")
public class RolePermission implements Serializable {
	public enum Permission {
		GRANTED,
		DENIED,
		IF_OWNER
	}

	public static class Queries {
		public static final String FIND_BY_METHOD_AND_ROLE = "FIND_BY_METHOD_AND_ROLE";
	}

	@Id
	@GeneratedValue
	@Getter
	@Setter //for JSONB deserialization
	private Integer id;

	@Getter
	@Setter
	private String role;

	@Getter
	@Setter
	private String operation;

	@Getter
	@Setter
	private Permission permission;
}
