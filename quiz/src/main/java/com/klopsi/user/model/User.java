package com.klopsi.user.model;

import com.klopsi.answer.model.Answer;
import com.klopsi.resource.model.Link;
import lombok.*;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@EqualsAndHashCode(exclude = {"links", "answers", "roles"})	// TODO why it does not work with roles in hashcode??
@ToString(exclude = {"links", "answers", "roles"})
@Entity
@Table(name = "users")
@NamedQuery(name = User.Queries.FIND_ALL, query = "select u from User u")
@NamedQuery(name = User.Queries.FIND_BY_LOGIN, query = "select u from User u where u.login = :login")
@NamedQuery(name = User.Queries.FIND_ALL_LOGINS, query = "select u.login from User u")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NamedEntityGraph(name = User.Graphs.WITH_ANSWER_AND_ROLE,
	attributeNodes = {@NamedAttributeNode("answers"), @NamedAttributeNode("roles")})
public class User implements Serializable {

	public static class Graphs {
		public static final String WITH_ANSWER_AND_ROLE = "User(Answer, Role)";
	}

	public static class Queries {
		public static final String FIND_ALL = "User.findAll";
		public static final String FIND_BY_LOGIN = "User.findByLogin";
		public static final String FIND_ALL_LOGINS = "User.findAllLogin";
	}

	public static class Roles {
		public static final String ADMIN = "ADMIN";
		public static final String USER = "USER";
		public static final String MODERATOR = "MODERATOR";
		public static final String ANONYMOUS = "ANONYMOUS";
	}

	@Id
	@GeneratedValue
	@Getter
	@Setter //for JSONB deserialization
	private Integer id;

	/**
	 * Unique user name.
	 */
	@Getter
	@Setter
	@Column(nullable = false, unique = true)
	private String login;

	/**
	 * User password.
	 */
	@Getter
	@Setter
	private String password;

	/**
	 * User roles.
	 */
	@Getter
	@Setter
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user"))
	@Column(name = "role")
	@Singular
	private List<String> roles;

	@Getter
	@Setter
	@NotBlank(message = "First name is required")
	private String firstName;

	@Getter
	@Setter
	@NotBlank(message = "Last name is required")
	private String lastName;

	@Getter
	@Setter
	@Column(name = "birth_date")
	@PastOrPresent(message = "Birthday date cannot be set in the future")
	@NotNull(message = "Birthday date must be provided")
	private LocalDate birthDate;

	@JsonbTransient
	@NotNull
	@Getter
	@Setter
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)	// answers are deleted with user
	private Set<Answer> answers;

	/**
	 * HATEOAS links.
	 */
	@JsonbProperty("_links")
	@Transient
	@Getter
	@Setter
	private Map<String, Link> links = new HashMap<>();

	public User(String firstName, String lastName, LocalDate birthDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
	}
}
