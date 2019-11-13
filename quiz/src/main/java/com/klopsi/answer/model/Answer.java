package com.klopsi.answer.model;

import com.klopsi.exercise.model.Exercise;
import com.klopsi.resource.model.Link;
import com.klopsi.user.model.User;
import lombok.*;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@EqualsAndHashCode(exclude = {"exercise", "user", "links"})
@ToString(exclude = {"exercise", "user", "links"})
@Entity
@Table(name = "answers")
@NamedQuery(name = Answer.Queries.FIND_ALL, query = "select a from Answer a")
@NamedQuery(name = Answer.Queries.FIND_BY_USER, query = "select a from Answer a where a.user.login = :login")
public class Answer implements Serializable {

	public static class Queries {
		public static final String FIND_ALL = "Answer.findAll";
		public static final String FIND_BY_USER = "Answer.findAllByUser";
	}

	@Id
	@GeneratedValue
	@Getter
	@Setter//for JSONB deserialization
	private Integer id;

	@Getter
	@Setter
	@NotBlank(message = "Answer must be specified")
	private String content;

	@Getter
	@Setter
	@Min(0)
	@Max(100)
	@NotNull(message = "Score must be specified")
	private Integer percent;

	@Getter
	@Setter
	@Column(name = "submission_date")
	@PastOrPresent(message = "Submission date cannot be set in the future")
	@NotNull(message = "Submission date must be provided")
	private LocalDate submissionDate;

	@Setter
	@ManyToOne
	@NotNull(message = "Chose exercise that you want to answer")
	@JoinColumn(name = "exercise")
	private Exercise exercise;

	@JsonbTransient
	public Exercise getExercise() {
		return this.exercise;
	}

	@Setter
	@ManyToOne
	@JoinColumn(name = "user")
	@NotNull(message = "Identify yourself")
	private User user;

	@JsonbTransient
	public User getUser() {
		return this.user;
	}

	/**
	 * HATEOAS links.
	 */
	@JsonbProperty("_links")
	@Transient
	@Getter
	@Setter
	private Map<String, Link> links = new HashMap<>();


	public Answer(String content, Integer percent, LocalDate submissionDate){
		this.content = content;
		this.percent = percent;
		this.submissionDate = submissionDate;
	}
}
