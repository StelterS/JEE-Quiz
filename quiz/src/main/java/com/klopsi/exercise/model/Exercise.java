package com.klopsi.exercise.model;

import com.klopsi.answer.model.Answer;
import com.klopsi.resource.model.Link;
import lombok.*;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@NoArgsConstructor
@EqualsAndHashCode(exclude = {"links", "answers"})
@ToString(exclude = {"links", "answers"})
@Entity
@Table(name = "exercise")
@NamedQuery(name = Exercise.Queries.FIND_ALL, query = "select e from Exercise e")
@NamedQuery(name = Exercise.Queries.COUNT, query = "select count(e) from Exercise e")
@NamedQuery(name = Exercise.Queries.FIND_BY_DIFFICULTY, query = "select e from Exercise e where e.difficulty in :difficulties")
public class Exercise implements Serializable {

	public static class Queries {
		public static final String FIND_ALL = "Exercise.findAll";
		public static final String COUNT = "Exercise.count";
		public static final String FIND_BY_DIFFICULTY = "Author.findByDifficulty";
	}

	@Id
	@GeneratedValue
	@Getter
	@Setter//for JSONB deserialization
	private Integer id;

	@Getter
	@Setter
	@NotBlank(message = "Title must be specified")
	private String title;

	@Getter
	@Setter
	@NotBlank(message = "Question to be answered must be specified")
	private String content;

	@Getter
	@Setter
	@NotNull
	@Enumerated(EnumType.STRING)
	private Difficulty difficulty;

	@Getter
	@Setter
	@Min(value = 0, message = "Value cannot be negative")
	@NotNull(message = "Maximum amount of points to get must be specified")
	private Integer maxPoints;

	@JsonbTransient
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "exercise", cascade = CascadeType.REMOVE)	// answers are deleted with exercise
	@Getter
	@Setter
	private List<Answer> answers = new ArrayList<>();

	/**
	 * HATEOAS links.
	 */
	@JsonbProperty("_links")
	@Transient
	@Getter
	@Setter
	private Map<String, Link> links = new HashMap<>();

	public Exercise(String title, String content, Difficulty difficulty, Integer maxPoints) {
		this.title = title;
		this.content = content;
		this.difficulty = difficulty;
		this.maxPoints = maxPoints;
	}

}
