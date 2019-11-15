package com.klopsi.user.model;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode()
@ToString()
@Entity
@Table(name = "permissions")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)

public class RolePermission implements Serializable {
	public enum Permission {
		GRANTED,
		DENIED,
		IF_OWNER
	}

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
