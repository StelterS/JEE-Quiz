package com.klopsi.answer.model;

import com.klopsi.exercise.model.Exercise;
import com.klopsi.resource.model.Link;
import com.klopsi.user.model.User;
import lombok.*;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class Answer implements Serializable {

	public static class Queries {
		public static final String FIND_ALL = "Answer.findAll";
	}

	@Id
	@GeneratedValue
	@Getter
	private Integer id;

	@Getter
	@Setter
	@NotBlank
	private String content;

	@Getter
	@Setter
	@Min(0)
	@Max(100)
	@NotNull
	private Integer percent;

	@Getter
	@Setter
	@Column(name = "submission_date")
	private LocalDate submissionDate;

	@Setter
	@ManyToOne
	@NotNull
	@JoinColumn(name = "exercise")
	private Exercise exercise;

	@JsonbTransient
	public Exercise getExercise() {
		return this.exercise;
	}

	@Setter
	@ManyToOne
	@JoinColumn(name = "user")
	@NotNull
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
