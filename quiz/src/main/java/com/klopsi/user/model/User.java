package com.klopsi.user.model;

import com.klopsi.answer.model.Answer;
import com.klopsi.resource.model.Link;
import lombok.*;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@EqualsAndHashCode(exclude = {"links", "answers"})
@ToString(exclude = {"links", "answers"})
@Entity
@Table(name = "users")
@NamedQuery(name = User.Queries.FIND_ALL, query = "select u from User u")
public class User implements Serializable {

	public static class Queries {
		public static final String FIND_ALL = "User.findAll";
	}

	@Id
	@GeneratedValue
	@Getter
	private Integer id;

	@Getter
	@Setter
	@NotBlank
	private String firstName;

	@Getter
	@Setter
	@NotBlank
	private String lastName;

	@Getter
	@Setter
	@Column(name = "birth_date")
	private LocalDate birthDate;

	@JsonbTransient
	@NotNull
	@Getter
	@Setter
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.REMOVE)	// answers are deleted with user
	private List<Answer> answers = new ArrayList<>();

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
