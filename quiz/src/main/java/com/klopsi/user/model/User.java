package com.klopsi.user.model;

import com.klopsi.answer.model.Answer;
import com.klopsi.resource.model.Link;
import lombok.*;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class User implements Serializable {
	private int id;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	private LocalDate birthDate;

	@JsonbTransient
	@NotNull
	private List<Answer> answers = new ArrayList<>();

	public User(User user) {
		this.id = user.id;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.birthDate = user.birthDate;
		this.answers = user.answers;
	}

	public User(int id, String firstName, String lastName, LocalDate birthDate, List<Answer> answers) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.answers = answers;
	}

	/**
	 * HATEOAS links.
	 */
	@JsonbProperty("_links")
	private Map<String, Link> links = new HashMap<>();
}
